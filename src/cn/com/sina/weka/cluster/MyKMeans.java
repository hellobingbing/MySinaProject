package cn.com.sina.weka.cluster;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;

import weka.clusterers.AbstractClusterer;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Capabilities;
import weka.core.CapabilitiesHandler;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
/**
 * 使用K-Means对新闻聚类
 * @author sina
 *
 */
public class MyKMeans {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//新闻特征数据路径
		String pathname = "." + File.separator + "cluster" + File.separator + "news.arff";
		try {
			getClusterResult(pathname);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 调用weka接口对新闻特征向量聚类，输出聚类结果
	 * @param pathname 要聚类的新闻数据的文件路径
	 * @throws Exception
	 */
	public static void getClusterResult(String pathname) throws Exception{
		Instances instances = null;
		SimpleKMeans KM = null;
		//默认欧几里得距离
		DistanceFunction disFun = null;
		//读入样本数据
		File file = new File(pathname);
		ArffLoader loader = new ArffLoader();
		loader.setFile(file);
		instances = loader.getDataSet();
		//删除第一个属性，由于其是新闻id
		instances.deleteAttributeAt(0);
		
		//初始化聚类器,加载算法
		KM = new SimpleKMeans();
		
		//设置参数
		KM.setNumClusters(20);
		KM.setSeed(10);
		KM.buildClusterer(instances);
		
		//打印结果
		//System.out.println(KM.toString());
		
		//获得模型参数设置	
		//for(String option : KM.getOptions()){
			//System.out.print(option + " ");
		//}
		//System.out.println();
		
		// the cluster to evaluate
		ClusterEvaluation eval = new ClusterEvaluation();
		// set the clusterer
		eval.setClusterer(KM);
		// the set of instances to cluster
		eval.evaluateClusterer(instances);
		// 评价聚类,打印聚类结果
		System.out.println(eval.clusterResultsToString());
		// 获得每条记录所属的聚簇 ,得到一个聚类结果的数组
		//double[] clusterResultForEach = eval.getClusterAssignments();
		// 
		//int i = 0;
		//Enumeration<Instance> enumeration = instances.enumerateInstances();
		//while(enumeration.hasMoreElements()){
			//System.out.print(enumeration.nextElement().toString());
			//System.out.println("\t" + clusterResultForEach[i]);
			//i++;
		//}
		
	}

}
