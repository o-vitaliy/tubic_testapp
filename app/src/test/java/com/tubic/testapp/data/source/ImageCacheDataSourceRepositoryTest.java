package com.tubic.testapp.data.source;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by ovitaliy on 27.02.2017.
 */
public class ImageCacheDataSourceRepositoryTest {

    private ImageCacheDataSourceImpl imageCacheDataSourceImpl;
    private File cacheFolder;

    @Before
    public void setUp() {
        cacheFolder = new File(System.getProperty("java.io.tmpdir"), "imageCache");
        imageCacheDataSourceImpl = new ImageCacheDataSourceImpl(cacheFolder);
    }

    @After
    public void tearDown() {
        cacheFolder.delete();
    }


    @Test
    public void testDownLoadImage() {
        String localLink = imageCacheDataSourceImpl.downloadImage("http://buyersguide.caranddriver.com/media/assets/submodel/7651.jpg").toBlocking().first();
        Assert.assertNotNull(localLink);
        File cachedFile = new File(cacheFolder, localLink);
        Assert.assertNotNull(cachedFile);
        Assert.assertTrue(cachedFile.exists());
        Assert.assertTrue(cachedFile.length() > 0);

        Boolean deleteResult = imageCacheDataSourceImpl.deleteImage(localLink).toBlocking().first();
        Assert.assertTrue(deleteResult);
        Assert.assertFalse(cachedFile.exists());
    }


}