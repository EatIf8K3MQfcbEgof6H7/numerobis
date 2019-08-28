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
import static io.github.mletkin.numerobis.Util.extractBuilder;
import static io.github.mletkin.numerobis.Util.uncheckExceptions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;

import io.github.mletkin.numerobis.generator.Facade;

/**
 * Mutator generation for generated internal builder.
 */
class MutatorInternalBuilderTest {

    @Test
    void mutatorForNonListField() {
        assertThat(generateFromResource("Mutator")).isEqualTo(//
                "public static class Builder {" //
                        + "    private Mutator product;" //
                        + "    public Builder() {" //
                        + "        product = new Mutator();" //
                        + "    }" //
                        + "    public Builder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public Mutator build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void mutatorsFortwoFieldsInOneDeclaration() {
        assertThat(generateFromResource("MutatorTwoFields")).isEqualTo(//
                "public static class Builder {" //
                        + "    private MutatorTwoFields product;" //
                        + "    public Builder() {" //
                        + "        product = new MutatorTwoFields();" //
                        + "    }" //
                        + "    public Builder withX(int x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public Builder withY(int y) {" //
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
                "public static class Builder {" //
                        + "    private MutatorWithCustomName product;" //
                        + "    public Builder() {" //
                        + "        product = new MutatorWithCustomName();" //
                        + "    }" //
                        + "    public Builder fillX(int x) {" //
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
                "public static class Builder {" //
                        + "    private FieldAnnoNoCustomName product;" //
                        + "    public Builder() {" //
                        + "        product = new FieldAnnoNoCustomName();" //
                        + "    }" //
                        + "    public Builder withX(int x) {" //
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
                "public static class Builder {" //
                        + "    private MutatorIgnore product;" //
                        + "    public Builder() {" //
                        + "        product = new MutatorIgnore();" //
                        + "    }" //
                        + "    public MutatorIgnore build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void mutatorForPrivateField() {
        assertThat(generateFromResource("MutatorPrivateField")).isEqualTo( //
                "public static class Builder {" //
                        + "    private MutatorPrivateField product;" //
                        + "    public Builder() {" //
                        + "        product = new MutatorPrivateField();" //
                        + "    }" //
                        + "    public Builder withY(int y) {" //
                        + "        product.y = y;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public MutatorPrivateField build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    @Test
    void adderForListField() {
        assertThat(generateFromResource("WithList")).isEqualTo(//
                "public static class Builder {" //
                        + "    private WithList product;" //
                        + "    public Builder() {" //
                        + "        product = new WithList();" //
                        + "    }" //
                        + "    public Builder withX(List<String> x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public Builder addX(String item) {" //
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
                "public static class Builder {" //
                        + "    private WithSet product;" //
                        + "    public Builder() {" //
                        + "        product = new WithSet();" //
                        + "    }" //
                        + "    public Builder withX(Set<String> x) {" //
                        + "        product.x = x;" //
                        + "        return this;" //
                        + "    }" //
                        + "    public Builder addX(String item) {" //
                        + "        product.x.add(item);" //
                        + "        return this;" //
                        + "    }" //
                        + "    public WithSet build() {" //
                        + "        return product;" //
                        + "    }" //
                        + "}");
    }

    private String generateFromResource(String className) {
        return uncheckExceptions(() -> asString(extractBuilder(//
                Facade.withConstructors(StaticJavaParser.parseResource(className + ".java"), className) //
                        .productUnit,
                className)));
    }

}