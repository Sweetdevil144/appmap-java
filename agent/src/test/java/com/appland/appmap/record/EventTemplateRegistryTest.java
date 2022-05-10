/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.appland.appmap.record;

import com.appland.appmap.test.util.ClassBuilder;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class EventTemplateRegistryTest {
  private static final EventTemplateRegistry registry = EventTemplateRegistry.get();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRegister() throws Exception {
    ArrayList<CtMethod> behaviorsToRegister = new ArrayList<CtMethod>();
    ClassPool classPool = ClassPool.getDefault();
    CtClass classType = classPool.get("com.appland.appmap.ExampleClass");

    behaviorsToRegister.add(classType.getDeclaredMethod("methodStaticZeroParam"));
    behaviorsToRegister.add(classType.getDeclaredMethod("methodStaticSingleParam"));
    behaviorsToRegister.add(classType.getDeclaredMethod("methodZeroParam"));
    behaviorsToRegister.add(classType.getDeclaredMethod("methodOneParam"));

    for (CtMethod method : behaviorsToRegister) {
      registry.register(method, new String[0]);
    }
  }

  CtClass buildCloneableEventTemplate() throws Exception {
    return new ClassBuilder("testCloneEventTemplateClass")
        .beginMethod()
        .setName("registeredMethod")
        .endMethod()
        .ctClass();
  }

  @Test
  public void testCloneEventTemplate() throws Exception {
    Integer index = registry.register(buildCloneableEventTemplate().getDeclaredMethod("registeredMethod"), new String[0]);
    assertTrue(registry.buildCallEvent(index) != null);
  }

  @Test
  public void testUnknownEventTemplate() throws Exception {
    thrown.expect(UnknownEventException.class);
    registry.buildCallEvent(Integer.MAX_VALUE);
  }
}
