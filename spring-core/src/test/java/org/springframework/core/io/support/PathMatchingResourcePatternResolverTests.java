/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.io.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;

/**
 * If this test case fails, uncomment diagnostics in the
 * {@link #assertProtocolAndFilenames} method.
 *
 * @author Oliver Hutchison
 * @author Juergen Hoeller
 * @author Chris Beams
 * @author Sam Brannen
 * @since 17.11.2004
 */
public class PathMatchingResourcePatternResolverTests {

	private static final String[] CLASSES_IN_CORE_IO_SUPPORT =
			new String[] {"EncodedResource.class", "LocalizedResourceHelper.class",
					"PathMatchingResourcePatternResolver.class", "PropertiesLoaderSupport.class",
					"PropertiesLoaderUtils.class", "ResourceArrayPropertyEditor.class",
					"ResourcePatternResolver.class", "ResourcePatternUtils.class"};

	private static final String[] TEST_CLASSES_IN_CORE_IO_SUPPORT =
			new String[] {"PathMatchingResourcePatternResolverTests.class"};

	private static final String[] CLASSES_IN_REACTIVESTREAMS =
			new String[] {"Processor.class", "Publisher.class", "Subscriber.class", "Subscription.class"};

	private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	/**
	 * 格式不合法
	 * @throws IOException
	 */
	@Test(expected = FileNotFoundException.class)
	public void invalidPrefixWithPatternElementInIt() throws IOException {
		resolver.getResources("xx**:**/*.xy");
	}

	/**
	 * 最后调用的是
	 * DefaultResourceLoader 类的 getResourceByPath(String path)
	 * 然后调用 ClassPathContextResource(path, getClassLoader())
	 * @throws IOException
	 */
	@Test
	public void singleResourceOnFileSystem() throws IOException {
		Resource[] resources =
				resolver.getResources("org/springframework/core/io/support/PathMatchingResourcePatternResolverTests.class");
		assertEquals(1, resources.length);
		assertProtocolAndFilenames(resources, "file", "PathMatchingResourcePatternResolverTests.class");
	}

	/**
	 * getResourceLoader().getResource(locationPattern) 最终还是调用的 DefaultResourceLoader 和上面一样
	 * @throws IOException
	 */
	@Test
	public void singleResourceInJar() throws IOException {
		Resource[] resources = resolver.getResources("org/reactivestreams/Publisher.class");
		assertEquals(1, resources.length);
		assertProtocolAndFilenames(resources, "jar", "Publisher.class");
	}

	@Ignore  // passes under Eclipse, fails under Ant
	@Test
	public void classpathStarWithPatternOnFileSystem() throws IOException {
		Resource[] resources = resolver.getResources("classpath*:org/springframework/core/io/sup*/*.class");
		// Have to exclude Clover-generated class files here,
		// as we might be running as part of a Clover test run.
		List<Resource> noCloverResources = new ArrayList<>();
		for (Resource resource : resources) {
			if (!resource.getFilename().contains("$__CLOVER_")) {
				noCloverResources.add(resource);
			}
		}
		resources = noCloverResources.toArray(new Resource[noCloverResources.size()]);
		assertProtocolAndFilenames(resources, "file",
				StringUtils.concatenateStringArrays(CLASSES_IN_CORE_IO_SUPPORT, TEST_CLASSES_IN_CORE_IO_SUPPORT));
	}

	@Test
	public void classpathWithPatternInJar() throws IOException {
		Resource[] resources = resolver.getResources("classpath:org/reactivestreams/*.class");
		assertProtocolAndFilenames(resources, "jar", CLASSES_IN_REACTIVESTREAMS);
	}

	@Test
	public void classpathStarWithPatternInJar() throws IOException {
		Resource[] resources = resolver.getResources("classpath*:org/reactivestreams/*.class");
		assertProtocolAndFilenames(resources, "jar", CLASSES_IN_REACTIVESTREAMS);
	}

	/**
	 * 自定义单元测试,classpath*: 开头,但是不含统配符
	 * 最后进入 findAllClassPathResources() 然后执行 doFindAllClassPathResources()
	 * @throws IOException
	 */
	@Test
	public void classpathStartWithOutPatternInJar() throws IOException {
		Resource[] resources = resolver.getResources("classpath*:org/");
		for (Resource resource : resources) {
			System.out.println(resource.getURL().getProtocol() + "-" + resource.getURL().getPath());
		}
	}

	/**
	 * doFindAllClassPathResources() 执行 addAllClassLoaderJarRoots()
	 * @throws IOException
	 */
	@Test
	public void classpathStartWithOutPatternAndPathIsNullInJar() throws IOException {
		Resource[] resources = resolver.getResources("classpath*:");
		for (Resource resource : resources) {
			System.out.println(resource.getURL().getProtocol() + "-" + resource.getURL().getPath());
		}
	}


	@Test
	public void rootPatternRetrievalInJarFiles() throws IOException {
		Resource[] resources = resolver.getResources("classpath*:*.dtd");
		boolean found = false;
		for (Resource resource : resources) {
			if (resource.getFilename().equals("aspectj_1_5_0.dtd")) {
				found = true;
			}
		}
		assertTrue("Could not find aspectj_1_5_0.dtd in the root of the aspectjweaver jar", found);
	}


	private void assertProtocolAndFilenames(Resource[] resources, String protocol, String... filenames)
			throws IOException {

		assertEquals("Correct number of files found", filenames.length, resources.length);
		for (Resource resource : resources) {
			String actualProtocol = resource.getURL().getProtocol();
			assertEquals(protocol, actualProtocol);
			assertFilenameIn(resource, filenames);
		}
	}

	private void assertFilenameIn(Resource resource, String... filenames) {
		String filename = resource.getFilename();
		assertTrue(resource + " does not have a filename that matches any of the specified names",
				Arrays.stream(filenames).anyMatch(filename::endsWith));
	}

}
