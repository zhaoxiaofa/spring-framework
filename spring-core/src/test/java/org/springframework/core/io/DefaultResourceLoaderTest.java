package org.springframework.core.io;

import org.junit.Test;

/**
 * @desc: ResourceLoader测试
 * @Author: zhaoxiaofa
 * @Date: 2019-08-29 10:57
 */
public class DefaultResourceLoaderTest {


	@Test
	public void testLoader0() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("/Users/zhaoxiaofa/develop/test/spring/hello");
		System.out.println("resource is FileSystemResource:" + (resource instanceof FileSystemResource)); // resource is FileSystemResource:false
		System.out.println("resource is ClassPathResource:" + (resource instanceof ClassPathResource)); // resource is ClassPathResource:true
	}

	@Test
	public void testLoader1() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("file:/Users/zhaoxiaofa/develop/test/spring/hello");
		System.out.println("resource is UrlResource:" + (resource instanceof UrlResource)); // resource is UrlResource:true
		System.out.println("resource is FileUrlResource:" + (resource instanceof FileUrlResource)); // resource is FileUrlResource:true
	}

	@Test
	public void testLoader2() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("https://www.google.com");
		System.out.println("resource is UrlResource:" + (resource instanceof UrlResource)); // resource is UrlResource:true
	}

	@Test
	public void testLoader3() {
		ResourceLoader resourceLoader = new FileSystemResourceLoader();
		Resource resource = resourceLoader.getResource("/Users/zhaoxiaofa/develop/test/spring/hello");
		System.out.println("resource is FileSystemResource:" + (resource instanceof FileSystemResource)); // resource is FileSystemResource:true
	}



}