package com.applet;
/**
 *  <b>日期：</b>2015年11月30日-下午11:23:22<br/>
 *  <b>Copyright (c)</b> 2015 广州天健软件有限公司<br/>
 */


/**
 * <b>类名称：</b>aa<br/>
 * <b>类描述：</b>TODO (描述这个类是做什么的)<br/>
 * <b>创建时间：</b>2015年11月30日 下午11:23:23<br/>
 * <b>备注：</b><br/>
 * 
 * @author Administrator <br />
 * @version 1.0.0<br/>
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JApplet;

public class ShowFileApplet extends JApplet
{

	private static final long serialVersionUID = 2780788007358428131L;

	private boolean isStandalone = false;

	private String content = "文件的内容是："; // 自定义的提示信息

	private String fileName = "C://WINNT//system.ini";// 读出这个文件的内容

	private TextArea ta = new TextArea(10, 80);// 自定义的输出框

	public String getParameter(String key, String def)
	{

		return isStandalone ? System.getProperty(key, def) :

		(getParameter(key) != null ? getParameter(key) : def);

	}

	public ShowFileApplet()
	{

	}

	public void init()
	{

		try
		{

			jbInit();

			myInit();// 自己定义的方法

		}

		catch (Exception e)
		{

			e.printStackTrace();

		}

	}

	private void jbInit() throws Exception
	{

		this.setSize(new Dimension(400, 300));

	}

	/**
	 * 
	 * 自定义的初始化方法，读入系统中的一个文件的内容并保存起来，然后，增加一个
	 * 
	 * 可视化的输出框
	 * 
	 */

	private void myInit()
	{

		String s;

		BufferedReader in;

		try
		{

			in = new BufferedReader(new FileReader(fileName));

			while ((s = in.readLine()) != null)
			{

				content += s + "/n";

			}

		} catch (IOException ex)
		{

			ex.printStackTrace();

		}

		System.out.println(content);

		ta.setText(content);

		getContentPane().add(ta);

	}

	/*
	 * 
	 * 重载的方法，输出内容
	 * 
	 **/

	public void paint(Graphics g)
	{

		ta.setText(content);

	}

	public String getAppletInfo()
	{

		return "Applet Information";

	}

	public String[][] getParameterInfo()
	{

		return null;

	}

	// static initializer for setting look & feel

	static
	{

		try
		{

		}

		catch (Exception e)
		{

		}

	}

}
