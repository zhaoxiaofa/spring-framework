package org.springframework.xiaofa;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * @desc:
 * @Author: zhaoxiaofa
 * @Date: 2019-09-03 21:00
 */
public class SpringHelloWorldTest {

	@Test
	public void testSpringHelloWorld() {

		BeanFactory beanFactory = new XmlBeanFactory(new DefaultResourceLoader().getResource("xiaofa/SpringHelloWorld.xml"));
		SpringHelloWord springHelloWorld = (SpringHelloWord) beanFactory.getBean("springHelloWorld");
		String result = springHelloWorld.helloWorld();
		System.out.println(result);
	}

	@Test
	public void testSpringHelloWorldWithName() {

		BeanFactory beanFactory = new XmlBeanFactory(new DefaultResourceLoader().getResource("xiaofa/SpringHelloWorldWithName.xml"));
		SpringHelloWord springHelloWorld = (SpringHelloWord) beanFactory.getBean("springHelloWorld");
		String result = springHelloWorld.helloWorld();
		System.out.println(result);
	}

	@Test
	public void testSpringHelloWorldWithOutIdAndName() {

		BeanFactory beanFactory = new XmlBeanFactory(new DefaultResourceLoader().getResource("xiaofa/SpringHelloWorldWithOutIdAndName.xml"));
		// xml文件没有定义id和name Spring会生成默认的beanName 下面这行代码执行时会报错
		SpringHelloWord springHelloWorld = (SpringHelloWord) beanFactory.getBean("springHelloWorld");
		String result = springHelloWorld.helloWorld();
		System.out.println(result);
	}

	@Test
	public void testSpringHelloWorldWithOutIdAndName1() {

		BeanFactory beanFactory = new XmlBeanFactory(new DefaultResourceLoader().getResource("xiaofa/SpringHelloWorldWithOutIdAndName.xml"));
		// 当没有指定 id 和 name 时，Spring 默认生成的 beanName 是 class
		SpringHelloWord springHelloWorld = (SpringHelloWord) beanFactory.getBean("org.springframework.xiaofa.SpringHelloWord");
		String result = springHelloWorld.helloWorld();
		System.out.println(result);
	}

}
