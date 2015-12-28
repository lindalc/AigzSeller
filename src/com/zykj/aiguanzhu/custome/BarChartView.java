package com.zykj.aiguanzhu.custome;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

/**
 * @author  lc 
 * @date 创建时间：2015-12-26 上午8:32:14 
 * @version 1.0 
 * @Description
 */
public class BarChartView {

	private static int margins[] = new int[] { 70, 70, 70, 70 };
	private static String[] titles = new String[] { "", "" };
	private List<double[]> values = new ArrayList<double[]>();
	private static int[] colors = new int[] { Color.GRAY, Color.GREEN };
	private XYMultipleSeriesRenderer renderer;
	private Context mContext;
	private String mTitle;
	private List<String> optionX;
	private List<String> optionY;
	private boolean isSingleView = false;

	public BarChartView(Context context, boolean isSingleView) {
		this.mContext = context;
		this.isSingleView = isSingleView;
		this.renderer = new XYMultipleSeriesRenderer();

	}

	public void initData(double[] firstAnswerPercent, double[] lastAnswerPercent, List<String> optionX,  List<String> optionY, String title) {
		this.values.add(firstAnswerPercent);
		if (!isSingleView) {
			this.values.add(lastAnswerPercent);
		}
		this.mTitle = title;
		this.optionX = optionX;
		this.optionY = optionY;
	}

	public View getBarChartView() {
		buildBarRenderer();
		setChartSettings(renderer, mTitle, "", "", 0, 7, 0, 40, Color.BLACK, Color.LTGRAY);
		renderer.getSeriesRendererAt(0).setDisplayChartValues(false);
		if(!isSingleView){
			 renderer.getSeriesRendererAt(1).setDisplayChartValues(false);
		}
		int sizeX =  optionX.size();
		for (int i = 0; i < sizeX; i++) {
			renderer.addXTextLabel(i, optionX.get(i));
		}
		int sizeY =  optionY.size();
		for (int i = 0; i < sizeY; i++) {
			renderer.addYTextLabel(i, optionY.get(i));
		}
		renderer.setMargins(margins);
		renderer.setMarginsColor(0x00ffffff);
		renderer.setPanEnabled(false, false);
		renderer.setZoomEnabled(false, false);// 设置x，y方向都不可以放大或缩�?
		renderer.setZoomRate(1.0f);
		renderer.setInScroll(false);
		renderer.setBackgroundColor(0x00ffffff);
		renderer.setApplyBackgroundColor(false);
		View view = ChartFactory.getBarChartView(mContext, buildBarDataset(titles, values), renderer, Type.DEFAULT); // Type.STACKED
		view.setBackgroundColor(0x00ffffff);
		return view;
	}

	private XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = isSingleView ? (titles.length - 1) : titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle,
			double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		renderer.setXLabels(0);
		renderer.setYLabels(0);
		renderer.setLabelsTextSize(25);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setXLabelsAlign(Align.CENTER);
		// renderer.setXLabelsColor(0xff000000);//设置X轴上的字体颜�?
		// renderer.setYLabelsColor(0,0xff000000);//设置Y轴上的字体颜�?

	}

	/*
	 * 初始化柱子
	 */
	protected void buildBarRenderer() {
		if (null == renderer) {
			return;
		}
		renderer.setBarWidth(23);
		renderer.setBarSpacing(20);
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(10);
		renderer.setLegendTextSize(10);
		int length = isSingleView ? (colors.length - 1) : colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(15);
			ssr.setDisplayChartValues(true);
			ssr.setColor(colors[i]);
			renderer.addSeriesRenderer(ssr);
		}
	}
}
