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

import static io.github.mletkin.numerobis.Util.asString;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import io.github.mletkin.numerobis.generator.Facade;

/**
 * Mutator generation for generated internal builder.
 */
class MutatorExternalBuilderTest {

    @Test
    void mutatorForNonListField() {
        assertThat(generateFromResource("Mutator")).isEqualTo(//
                "public class MutatorBuilder {" //
                        + "    private Mutator product;" //
                        + "    public MutatorBuilder() {" //
                        + "        product = new Mutator();" //
                        + "    }" //
                        + "    public MutatorBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public Mutator build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void mutatorsForTwoFieldsInOneDeclaration() {
        assertThat(generateFromResource("MutatorTwoFields")).isEqualTo(//
                "public class MutatorTwoFieldsBuilder {" //
                        + "    private MutatorTwoFields product;" //
                        + "    public MutatorTwoFieldsBuilder() {" //
                        + "        product = new MutatorTwoFields();" //
                        + "    }" //
                        + "    public MutatorTwoFieldsBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public MutatorTwoFieldsBuilder withY(int y) {" //
                        + "        product.y = y;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public MutatorTwoFields build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void mutatorForFieldWithCustomNameAnnotation() {
        assertThat(generateFromResource("MutatorWithCustomName")).isEqualTo(//
                "public class MutatorWithCustomNameBuilder {" //
                        + "    private MutatorWithCustomName product;" //
                        + "    public MutatorWithCustomNameBuilder() {" //
                        + "        product = new MutatorWithCustomName();" //
                        + "    }" //
                        + "    public MutatorWithCustomNameBuilder fillX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public MutatorWithCustomName build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void mutatorForFieldWithAnnotationWithoutCustomMethodName() {
        assertThat(generateFromResource("FieldAnnoNoCustomName")).isEqualTo(//
                "public class FieldAnnoNoCustomNameBuilder {" //
                        + "    private FieldAnnoNoCustomName product;" //
                        + "    public FieldAnnoNoCustomNameBuilder() {" //
                        + "        product = new FieldAnnoNoCustomName();" //
                        + "    }" //
                        + "    public FieldAnnoNoCustomNameBuilder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public FieldAnnoNoCustomName build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void noMutatorForFieldWithIgnoreAnnotation() {
        assertThat(generateFromResource("MutatorIgnore")).isEqualTo(//
                "public class MutatorIgnoreBuilder {" //
                        + "    private MutatorIgnore product;" //
                        + "    public MutatorIgnoreBuilder() {" //
                        + "        product = new MutatorIgnore();" //
                        + "    }" //
                        + "    public MutatorIgnore build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void noMutatorForPrivateField() {
        assertThat(generateFromResource("MutatorPrivateField")).isEqualTo( //
                "public class MutatorPrivateFieldBuilder {" //
                        + "    private MutatorPrivateField product;" //
                        + "    public MutatorPrivateFieldBuilder() {" //
                        + "        product = new MutatorPrivateField();" //
                        + "    }" //
                        + "    public MutatorPrivateField build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void adderForListField() {
        assertThat(generateFromResource("WithList")).isEqualTo(//
                "import java.util.List;" //
                        + "public class WithListBuilder {" //
                        + "    private WithList product;" //
                        + "    public WithListBuilder() {" //
                        + "        product = new WithList();" //
                        + "    }" //
                        + "    public WithListBuilder withX(List<String> x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public WithListBuilder addX(String item) {" //
                        + "        product.x.add(item);" //
                        + "        return this;" //
                        + "    }" //
                        + "    public WithList build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void adderForSetField() {
        assertThat(generateFromResource("WithSet")).isEqualTo(//
                "import java.util.Set;" //
                        + "public class WithSetBuilder {" //
                        + "    private WithSet product;" //
                        + "    public WithSetBuilder() {" //
                        + "        product = new WithSet();" //
                        + "    }" //
                        + "    public WithSetBuilder withX(Set<String> x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public WithSetBuilder addX(String item) {" //
                        + "        product.x.add(item);" //
                        + "        return this;" //
                        + "    }" //
                        + "    public WithSet build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    private String generateFromResource(String className) {
        return Util.uncheckExceptions(
                () -> asString(Facade.withConstructors(StaticJavaParser.parseResource(className + ".java"), className,
                        new CompilationUnit()).builderUnit));
    }

}