package com.batstateu_ros_cpt.attendancesystem2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented Factories, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under Factories.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.batstateu_ros_cpt.attendancesystem2", appContext.getPackageName());
    }
}
