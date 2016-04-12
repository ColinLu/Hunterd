package com.colin.hunter.help;

import android.content.Context;
import android.graphics.Bitmap;

import com.colin.hunter.R;
import com.colin.hunter.data.Constants;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

public class ImageLoaderHelp {


    /**
     * 设置缓存  官方写法
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // 初始化ImageLoader
        File cachefile = new File(Constants.IMAGE_LOAD_CACHE_PATH);// 获取到缓存的目录地址
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        // config.discCacheFileCount(100) //缓存的File数量
//        config.diskCache(new UnlimitedDiskCache(cachefile));// 自定义缓存路径
//        config.writeDebugLogs(); // Remove for release app
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());

        // 获取本地缓存的目录， 该目录在sd卡的根目
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        // 线程池加载数量
        builder.threadPoolSize(3);
        // 设定线程优先级
        builder.threadPriority(Thread.NORM_PRIORITY - 2);
        // 设定内存为弱缓存
        // builder.memoryCache(new WeakMemoryCache());
        // 设定内存图片缓存大小的限制，不设置默认为屏幕的宽,即保存的每个缓存文件的最大长宽
        builder.memoryCacheExtraOptions(480, 800);
        // 设置硬盘缓存50MiB
        builder.diskCacheSize(50 * 1024 * 1024);
        // builder.memoryCacheSize(1 * 1024 * 1024);
        builder.denyCacheImageMultipleSizesInMemory();
        // 设定缓存的路径
        builder.diskCache(new UnlimitedDiskCache(cachefile));// 自定义缓存路径
        // 设定网络连接超时timeout:10s 读取网络连接超时read timeout 60s
        builder.imageDownloader(new BaseImageDownloader(context, 10 * 1000, 60 * 1000));
        // 设置ImageLoader的配置参
        builder.defaultDisplayImageOptions(initDisplayOptions());
        // 设置图片请求打印日志
        builder.writeDebugLogs(); // Remove for release app
        // 初始化imageLoader
        ImageLoader.getInstance().init(builder.build());
    }


    // EXACTLY :图像将完全按比例缩小的目标大小
    // EXACTLY_STRETCHED:图片会缩放到目标大小完全
    // IN_SAMPLE_INT:图像将被二次采样的整数倍
    // IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
    // NONE:图片不会调整

    /**
     * 配置ImageLoder
     */
    public static DisplayImageOptions initDisplayOptions() {

        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.mipmap.image_loading) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.image_load_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.image_load_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 设置图片下载前的延迟
                .delayBeforeLoading(100)// delayInMillis为你设置的延迟时间
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
        return options;
    }
}
