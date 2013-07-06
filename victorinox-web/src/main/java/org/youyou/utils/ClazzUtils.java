package org.youyou.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author youpengfeiinfo
 * @version 13-6-16
 */
public class ClazzUtils {

    public static final String FILE_PROTOCOL = "file";
    public static final String JAR_PROTOCOL = "jar";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String CLASS_SUFFIX = ".class";
    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if (FILE_PROTOCOL.equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), DEFAULT_ENCODING);
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if (JAR_PROTOCOL.equals(protocol)) {
                    findAndAddClassesInPackageByJar(classes, recursive, packageName, packageDirName, url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByJar(Set<Class<?>> clazzSet, boolean recursive, String packageName, String packageDirName, URL url) throws ClassNotFoundException, IOException {

        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entries = jarFile.entries();
        // 同样的进行循环迭代
        while (entries.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 如果是以/开头的
            // 获取后面的字符串
            name = name.charAt(0) == '/' ? name.substring(1) : name;
            // 如果前半部分和定义的包名相同
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                // 如果以"/"结尾 是一个包
                // 获取包名 把"/"替换成"."
                packageName = idx != -1 ? name.substring(0, idx).replace('/', '.') : packageName;
                // 如果可以迭代下去 并且是一个包
                if ((idx != -1) || recursive) {
                    // 如果是一个.class文件 而且不是目录
                    if (!entry.isDirectory() && name.endsWith(CLASS_SUFFIX)) {
                        String className = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
                        clazzSet.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                    }
                }
            }
        }

    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) throws ClassNotFoundException {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirFiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(CLASS_SUFFIX));
            }
        });
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                final String fileName = file.getName();
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));

            }
        }
    }
}
