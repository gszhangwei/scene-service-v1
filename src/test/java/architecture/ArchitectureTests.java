package architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.Test;

public class ArchitectureTests {
    private static JavaClasses packages = (new ClassFileImporter()).withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages("com.scene");

    @Test
    public final void domain_layer_depend_on_rule() {
        ArchRuleDefinition.classes().that().resideInAPackage("..domain..").should().onlyDependOnClassesThat(JavaClass.Predicates.resideInAnyPackage(new String[]{"java..", "..domain..", "org.jetbrains.annotations..", "..hashids..", "..tika..", "org.springframework..", "javax.validation.constraints..", "org.apache.commons.lang3.."}).or(JavaClass.Predicates.type(byte[].class))).check(packages);
    }

    @Test
    public final void layered_architecture_depend_on_rule() {
        Architectures.layeredArchitecture().layer("adapters").definedBy("..adapters..").layer("application").definedBy("..application..").layer("domain").definedBy("..domain..").whereLayer("adapters").mayNotBeAccessedByAnyLayer().whereLayer("application").mayOnlyBeAccessedByLayers(new String[]{"adapters"}).check(packages);
    }

    @Test
    public final void rest_adapter_depend_on_rule() {
        ArchRuleDefinition.classes().that().resideInAPackage("..inbound.rest..").should().onlyBeAccessed().byAnyPackage(new String[]{"..inbound.rest.."}).check(packages);
    }

    @Test
    public final void sql_adapter_depend_on_rule() {
        ArchRuleDefinition.classes().that().resideInAPackage("..outbound.sqlstorage..").should().onlyBeAccessed().byAnyPackage(new String[]{"..outbound.sqlstorage.."}).check(packages);
    }

    @Test
    public final void blob_adapter_depend_on_rule() {
        ArchRuleDefinition.classes().that().resideInAPackage("..outbound.blobstorage..").should().onlyBeAccessed().byAnyPackage(new String[]{"..outbound.blobstorage.."}).check(packages);
    }

    @Test
    public final void config_adapter_depend_on_rule() {
        ArchRuleDefinition.classes().that().resideInAPackage("..outbound.config..").should().onlyBeAccessed().byAnyPackage(new String[]{"..outbound.config.."}).check(packages);
    }
}
