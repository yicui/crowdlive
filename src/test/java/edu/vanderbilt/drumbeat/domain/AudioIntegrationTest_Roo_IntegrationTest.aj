// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.domain;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.AudioIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AudioIntegrationTest_Roo_IntegrationTest {
    
    declare @type: AudioIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: AudioIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: AudioIntegrationTest: @Transactional;
    
    @Autowired
    private AudioDataOnDemand AudioIntegrationTest.dod;
    
    @Test
    public void AudioIntegrationTest.testCountAudios() {
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", dod.getRandomAudio());
        long count = Audio.countAudios();
        Assert.assertTrue("Counter for 'Audio' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void AudioIntegrationTest.testFindAudio() {
        Audio obj = dod.getRandomAudio();
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Audio' failed to provide an identifier", id);
        obj = Audio.findAudio(id);
        Assert.assertNotNull("Find method for 'Audio' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Audio' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void AudioIntegrationTest.testFindAllAudios() {
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", dod.getRandomAudio());
        long count = Audio.countAudios();
        Assert.assertTrue("Too expensive to perform a find all test for 'Audio', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Audio> result = Audio.findAllAudios();
        Assert.assertNotNull("Find all method for 'Audio' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Audio' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void AudioIntegrationTest.testFindAudioEntries() {
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", dod.getRandomAudio());
        long count = Audio.countAudios();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Audio> result = Audio.findAudioEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Audio' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Audio' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void AudioIntegrationTest.testFlush() {
        Audio obj = dod.getRandomAudio();
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Audio' failed to provide an identifier", id);
        obj = Audio.findAudio(id);
        Assert.assertNotNull("Find method for 'Audio' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAudio(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Audio' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void AudioIntegrationTest.testMergeUpdate() {
        Audio obj = dod.getRandomAudio();
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Audio' failed to provide an identifier", id);
        obj = Audio.findAudio(id);
        boolean modified =  dod.modifyAudio(obj);
        Integer currentVersion = obj.getVersion();
        Audio merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Audio' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void AudioIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", dod.getRandomAudio());
        Audio obj = dod.getNewTransientAudio(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Audio' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Audio' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Audio' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void AudioIntegrationTest.testRemove() {
        Audio obj = dod.getRandomAudio();
        Assert.assertNotNull("Data on demand for 'Audio' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Audio' failed to provide an identifier", id);
        obj = Audio.findAudio(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Audio' with identifier '" + id + "'", Audio.findAudio(id));
    }
    
}