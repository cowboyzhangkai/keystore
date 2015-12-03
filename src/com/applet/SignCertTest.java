package com.applet;
/**
 *  <b>日期：</b>2015年11月30日-下午5:01:03<br/>
 *  <b>Copyright (c)</b> 2015 广州天健软件有限公司<br/>
 */


import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.crypto.Cipher;

/**
 * <b>类名称：</b>SignCertTest<br/>
 * <b>类描述：</b>TODO (描述这个类是做什么的)<br/>
 * <b>创建时间：</b>2015年11月30日 下午5:01:03<br/>
 * <b>备注：</b><br/>
 * 
 * @author Administrator <br />
 * @version 1.0.0<br/>
 */
public class SignCertTest
{
	public static void main(String[] args)
	{
		try
		{
			// 前提：JDK已安装且正确配置环境变量
			// 首先在C盘建立目录 MyKeyStore，用来存放证书库以及导出的证书文件，然后在命令行执行下列2句
			// 下句含义：在当前目录创建 TestStore 密钥库，库密码 aaaaaa ，创建证书 TestKey2 ：非对称密钥，RSA
			// 算法，key密码为 bbbbbb ，存于 TestStore
			// C:/MyKeyStore > keytool -genkey -alias TestKey2 -dname
			// "CN=test222"
			// -keyalg RSA -keystore TestStore -storepass aaaaaa -keypass bbbbbb
			// 下句含义：将 TestStore 库中的 TestKey2 导出为证书文件 TestKey2.cer ，这里可能需要将
			// export
			// 修改为 exportcert
			// C:/MyKeyStore > keytool -export -alias TestKey2 -file
			// TestKey2.cer
			// -keystore TestStore -storepass aaaaaa
			// 证书库证书保存证书的公私钥，导出的证书文件只携带公钥

			byte[] msg = "犯大汉天威者，虽远必诛！".getBytes("UTF8"); // 待加解密的消息

			// 用证书的公钥加密
			CertificateFactory cff = CertificateFactory.getInstance("X.509");
			FileInputStream fis1 = new FileInputStream("C://keystore//abnerca.cer"); // 证书文件
			Certificate cf = cff.generateCertificate(fis1);
			PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
			Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
			c1.init(Cipher.ENCRYPT_MODE, pk1);
			byte[] msg1 = c1.doFinal(msg); // 加密后的数据

			// 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中
			FileInputStream fis2 = new FileInputStream("C://keystore//abnerCALib");
			KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
			char[] kspwd = "111111".toCharArray(); // 证书库密码
			char[] keypwd = "333333".toCharArray(); // 证书密码
			ks.load(fis2, kspwd); // 加载证书
			//mission_water_signed mission_water
			PrivateKey pk2 = (PrivateKey) ks.getKey("abnerca", keypwd); // 获取证书私钥
			fis2.close();
			Cipher c2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			c2.init(Cipher.DECRYPT_MODE, pk2);
			byte[] msg2 = c2.doFinal(msg1); // 解密后的数据

			// 打印解密字符串 - 应显示 犯大汉天威者，虽远必诛！
			System.out.println(new String(msg2, "UTF8")); // 将解密数据转为字符串
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}