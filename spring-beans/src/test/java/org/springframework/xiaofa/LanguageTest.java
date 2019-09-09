package org.springframework.xiaofa;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc:
 * @Author: zhaoxiaofa
 * @Date: 2019-09-06 14:51
 */
public class LanguageTest {

	@Test
	public void test() {
		BeanFactory beanFactory = new XmlBeanFactory(new DefaultResourceLoader().getResource("xiaofa/LookupMethod.xml"));
		AbstractLanguage abstractLanguage = (AbstractLanguage) beanFactory.getBean("abstractLanguage");
		abstractLanguage.sayHello();

	}


	@Test
	public void rangeTest(){
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			list.add(i);
		}
	}

}
