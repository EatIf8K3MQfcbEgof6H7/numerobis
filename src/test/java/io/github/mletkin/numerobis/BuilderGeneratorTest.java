/**
 * (c) 2019 by Ullrich Rieger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mletkin.numerobis;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;

/**
 * Builder generation with empty target unit.
 */
class BuilderGeneratorTest {

    @Test
    void convertsClassWithField() {
        Assertions.assertThat(generateFromResource("TestClass")).isEqualTo(//
                "public class TestClassBuilder {" //
                        + "    private TestClass product;" //
                        + "    public TestClassBuilder() {" //
                        + "        product = new TestClass();" //
                        + "    }" //
                        + "    public TestClassBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClass build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Disabled
    @Test
    void usesCustomMethodName() {
        Assertions.assertThat(generateFromResource("TestClassWithName")).isEqualTo(//
                "public class TestClassWithNameBuilder {" //
                        + "    private TestClassWithName product = new TestClassWithName();" //
                        + "    public TestClassWithNameBuilder access(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassWithName build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void fieldWithAnnotationIsIgnored() {
        Assertions.assertThat(generateFromResource("TestClassIgnoreField")).isEqualTo(//
                "public class TestClassIgnoreFieldBuilder {" //
                        + "    private TestClassIgnoreField product;" //
                        + "    public TestClassIgnoreFieldBuilder() {" //
                        + "        product = new TestClassIgnoreField();" //
                        + "    }" //
                        + "    public TestClassIgnoreFieldBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassIgnoreField build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void convertsClassWithTwoFields() {
        Assertions.assertThat(generateFromResource("TestClassTwo")).isEqualTo(//
                "public class TestClassTwoBuilder {" //
                        + "    private TestClassTwo product;" //
                        + "    public TestClassTwoBuilder() {" //
                        + "        product = new TestClassTwo();" //
                        + "    }" //
                        + "    public TestClassTwoBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassTwoBuilder withY(int y) {" //
                        + "        product.y = y;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassTwo build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void ignoresPrivateField() {
        Assertions.assertThat(generateFromResource("TestClassWithPrivateField")).isEqualTo( //
                "public class TestClassWithPrivateFieldBuilder {" //
                        + "    private TestClassWithPrivateField product;" //
                        + "    public TestClassWithPrivateFieldBuilder() {" //
                        + "        product = new TestClassWithPrivateField();" //
                        + "    }" //
                        + "    public TestClassWithPrivateFieldBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassWithPrivateField build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void builderIsInSamePackage() {
        Assertions.assertThat(generateFromResource("TestClassWithPackage")).isEqualTo(//
                "package foo.bar.baz;" //
                        + "public class TestClassWithPackageBuilder {" //
                        + "    private TestClassWithPackage product;" //
                        + "    public TestClassWithPackageBuilder() {" //
                        + "        product = new TestClassWithPackage();" //
                        + "    }" //
                        + "    public TestClassWithPackageBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public TestClassWithPackage build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void withConstructor() {
        Assertions.assertThat(generateFromResource("TestClassWithConstructor")).isEqualTo( //
                "public class TestClassWithConstructorBuilder {" + //
                        "    private TestClassWithConstructor product;" + //
                        "    public TestClassWithConstructorBuilder(int n) {" + //
                        "        product = new TestClassWithConstructor(n);" + //
                        "    }" + //
                        "    public TestClassWithConstructorBuilder withX(int x) {" + //
                        "        product.x = x;" + //
                        "        return this;" + //
                        "    }" + //
                        "    public TestClassWithConstructor build() {" + //
                        "        return product;" + //
                        "    }" + //
                        "}");
    }

    @Test
    void ignoreConstructorWithAnnotation() {
        Assertions.assertThat(generateFromResource("TestClassIgnoreConstructor")).isEqualTo( //
                "public class TestClassIgnoreConstructorBuilder {" + //
                        "    private TestClassIgnoreConstructor product;" + //
                        "    public TestClassIgnoreConstructorBuilder(int n) {" + //
                        "        product = new TestClassIgnoreConstructor(n);" + //
                        "    }" + //
                        "    public TestClassIgnoreConstructorBuilder withX(int x) {" + //
                        "        product.x = x;" + //
                        "        return this;" + //
                        "    }" + //
                        "    public TestClassIgnoreConstructor build() {" + //
                        "        return product;" + //
                        "    }" + //
                        "}");
    }

    @Test
    void copiesImport() {
        Assertions.assertThat(generateFromResource("TestClassWithImport")).isEqualTo(//
                "import foo.bar.baz;" + //
                        "public class TestClassWithImportBuilder {" //
                        + "    private TestClassWithImport product;" //
                        + "    public TestClassWithImportBuilder() {" //
                        + "        product = new TestClassWithImport();" //
                        + "    }" //
                        + "    public TestClassWithImport build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void ignoresBuilderImport() {
        Assertions.assertThat(generateFromResource("TestClassWithBuilderImport")).isEqualTo(//
                        "public class TestClassWithBuilderImportBuilder {" //
                        + "    private TestClassWithBuilderImport product;" //
                        + "    public TestClassWithBuilderImportBuilder() {" //
                        + "        product = new TestClassWithBuilderImport();" //
                        + "    }" //
                        + "    public TestClassWithBuilderImport build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void notContainedClassProducesNothing() {
        Assertions.assertThatExceptionOfType(GeneratorException.class).isThrownBy( //
                () -> BuilderGenerator.generate(StaticJavaParser.parseResource("TestClass.java"), "Foo"))
                .withMessage("Product class not found in compilation unit.");
    }

    @Test
    void nullClassProducesNothing() {
        Assertions.assertThatExceptionOfType(GeneratorException.class).isThrownBy( //
                () -> BuilderGenerator.generate(StaticJavaParser.parseResource("TestClass.java"), ""))
                .withMessage("Product class not found in compilation unit.");
    }

    @Test
    void classWithoutUsableConstructorThrowsException() {
        Assertions.assertThatExceptionOfType(GeneratorException.class).isThrownBy( //
                () -> generateFromResource("TestClassWithoutConstructor"))
                .withMessage("No suitable constructor found.");
    }

    private String generateFromResource(String className) {
        try {
            return BuilderGenerator.generate(StaticJavaParser.parseResource(className + ".java"), className).toString()
                    .replace("\r\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test2() throws IOException {
        System.out.println(BuilderGenerator
                .generate(StaticJavaParser.parseResource("TestClassWithConstructor.java"), "TestClassWithConstructor")
                .toString());
    }

}
