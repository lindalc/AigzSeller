/*
 * Copyright 2011 Sina.
 *
 * Licensed under the Apache License and Weibo License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.open.weibo.com
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zykj.aiguanzhu.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import android.os.Bundle;
import android.util.Log;

/**
 * Encapsulation a abstract weibo http headers base class.
 * 
 * @author ZhangJie (zhangjie2@staff.sina.com.cn)
 */

public abstract class HttpHeaderFactory {
    public static final String CONST_HMAC_SHA1 = "HmacSHA1";
    public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";
    public static final String CONST_OAUTH_VERSION = "1.0";

    public HttpHeaderFactory() {
    }

    public String getWeiboAuthHeader(String method, String url, AigzParameters params){
    	try {
    		final long timestamp = System.currentTimeMillis() / 1000;
            final long nonce = timestamp + (new Random()).nextInt();
            // step 2: authParams�������ô���1.���ܴ�һ���� 2.������Authorizationͷ��
            AigzParameters authParams = this.generateAuthParameters(nonce, timestamp);
            // ������ڼ���signature�ģ�����
            AigzParameters signatureParams = this.generateSignatureParameters(authParams, params, url);
            // step 3: �������ǩ���base String
            String oauthBaseString = this.generateAuthSignature(method, signatureParams, url);
            // step 4: ���oauth_signature
            String signature = generateSignature(oauthBaseString);
            authParams.add("oauth_signature", signature);
            // step 5: for additional parameters
            this.addAdditionalParams(authParams, params);
            Log.e("Oauth", "OAuth ");
            return "OAuth " + encodeParameters(authParams, ",", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
    		
    }

    private String generateAuthSignature(final String method, AigzParameters signatureParams,
            final String url) {
        StringBuffer base = new StringBuffer(method).append("&")
                .append(encode(constructRequestURL(url))).append("&");
        base.append(encode(encodeParameters(signatureParams, "&", false)));
        String oauthBaseString = base.toString();
        return oauthBaseString;
    }

    private AigzParameters generateSignatureParameters(AigzParameters authParams,
    		AigzParameters params, String url){
    	AigzParameters signatureParams = new AigzParameters();
        signatureParams.addAll(authParams);
        signatureParams.addAll(params);
        this.parseUrlParameters(url, signatureParams);
        AigzParameters lsp = generateSignatureList(signatureParams);
        return lsp;
    }

    private AigzParameters generateAuthParameters(long nonce, long timestamp){
    	AigzParameters authParams = new AigzParameters();
        authParams.add("oauth_nonce", String.valueOf(nonce));
        authParams.add("oauth_signature_method", HttpHeaderFactory.CONST_SIGNATURE_METHOD);
        authParams.add("oauth_timestamp", String.valueOf(timestamp));
        authParams.add("oauth_version", HttpHeaderFactory.CONST_OAUTH_VERSION);
        return authParams;
    }

    // ������ڹ�ϣ��base string����ע��Ҫ��˳�򣬰����ĵ����������ɣ�����40107����
    public abstract AigzParameters generateSignatureList(AigzParameters bundle);

    // add additional parameters to des key-value pairs,support to expanding
    // params
    public abstract void addAdditionalParams(AigzParameters des, AigzParameters src);

    // ����url�в����?,�洢��signatureBaseParams
    public void parseUrlParameters(String url, AigzParameters signatureBaseParams){
        int queryStart = url.indexOf("?");
        if (-1 != queryStart) {
            String[] queryStrs = url.substring(queryStart + 1).split("&");
            try {
                for (String query : queryStrs) {
                    String[] split = query.split("=");
                    if (split.length == 2) {
                        signatureBaseParams.add(URLDecoder.decode(split[0], "GBK"),
                                URLDecoder.decode(split[1], "GBK"));
                    } else {
                        signatureBaseParams.add(URLDecoder.decode(split[0], "GBK"), "");
                    }
                }
            } catch (UnsupportedEncodingException e) {
            	e.printStackTrace();
            }

        }

    }

    public abstract String generateSignature(String data);

    public static String encodeParameters(AigzParameters postParams, String splitter, boolean quot) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < postParams.size(); i++) {
            if (buf.length() != 0) {
                if (quot) {
                    buf.append("\"");
                }
                buf.append(splitter);
            }
            buf.append(encode(postParams.getKey(i))).append("=");
            if (quot) {
                buf.append("\"");
            }
            buf.append(encode(postParams.getValue(i)));
        }
        if (buf.length() != 0) {
            if (quot) {
                buf.append("\"");
            }
        }
        return buf.toString();
    }

    public static String encodeParameters(Bundle postParams, String split, boolean quot) {
        final String splitter = split;
        StringBuffer buf = new StringBuffer();
        for (String key : postParams.keySet()) {
            if (buf.length() != 0) {
                if (quot) {
                    buf.append("\"");
                }
                buf.append(splitter);
            }
            buf.append(encode(key)).append("=");
            if (quot) {
                buf.append("\"");
            }
            buf.append(encode(postParams.getString(key)));
        }
        if (buf.length() != 0) {
            if (quot) {
                buf.append("\"");
            }
        }
        return buf.toString();
    }

    //
    public static String constructRequestURL(String url) {
        int index = url.indexOf("?");
        if (-1 != index) {
            url = url.substring(0, index);
        }
        int slashIndex = url.indexOf("/", 8);
        String baseURL = url.substring(0, slashIndex).toLowerCase();
        int colonIndex = baseURL.indexOf(":", 8);
        if (-1 != colonIndex) {
            // url contains port number
            if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
                // http default port 80 MUST be excluded
                baseURL = baseURL.substring(0, colonIndex);
            } else if (baseURL.startsWith("https://") && baseURL.endsWith(":443")) {
                // http default port 443 MUST be excluded
                baseURL = baseURL.substring(0, colonIndex);
            }
        }
        url = baseURL + url.substring(slashIndex);

        return url;
    }

    /**
     * @param value
     *            string to be encoded
     * @return encoded parameters string
     */
    public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "GBK");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuffer buf = new StringBuffer(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length() && encoded.charAt(i + 1) == '7'
                    && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }

}
