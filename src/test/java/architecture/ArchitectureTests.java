package architecture;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import org.junit.Test;

public class ArchitectureTests {
    private static JavaClasses packages = new ClassFileImporter()
            .withImportOption(DO_NOT_INCLUDE_TESTS)
            .importPackages("com.scene");

    @Test
    public final void domain_layer_depend_on_rule() {
        classes().that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat(
                        resideInAnyPackage("java..", "..domain..", "..hashids..", "..tika..")
                                .or(type(byte[].class)))
                .check(packages);
    }

    @Test
    public final void layered_architecture_depend_on_rule() {
        layeredArchitecture()
                .layer("adapters").definedBy("..adapters..")
                .layer("application").definedBy("..application..")
                .layer("domain").definedBy("..domain..")
                .whereLayer("adapters").mayNotBeAccessedByAnyLayer()
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapters")
                .check(packages);
    }

    @Test
    public final void rest_adapter_depend_on_rule() {
        classes().that().resideInAPackage("..inbound.rest..")
                .should().onlyBeAccessed().byAnyPackage("..inbound.rest..")
                .check(packages);
    }

    @Test
    public final void sql_adapter_depend_on_rule() {
        classes().that().resideInAPackage("..outbound.sqlstorage..")
                .should().onlyBeAccessed().byAnyPackage("..outbound.sqlstorage..")
                .check(packages);
    }

    @Test
    public final void blob_adapter_depend_on_rule() {
        classes().that().resideInAPackage("..outbound.blobstorage..")
                .should().onlyBeAccessed().byAnyPackage("..outbound.blobstorage..")
                .check(packages);
    }

    @Test
    public final void config_adapter_depend_on_rule() {
        classes().that().resideInAPackage("..outbound.config..")
                .should().onlyBeAccessed().byAnyPackage("..outbound.config..")
                .check(packages);
    }
}
