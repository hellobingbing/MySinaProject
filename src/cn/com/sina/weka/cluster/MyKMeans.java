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
 * ʹ��K-Means�����ž���
 * @author sina
 *
 */
public class MyKMeans {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//������������·��
		String pathname = "." + File.separator + "cluster" + File.separator + "news.arff";
		try {
			getClusterResult(pathname);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ����weka�ӿڶ����������������࣬���������
	 * @param pathname Ҫ������������ݵ��ļ�·��
	 * @throws Exception
	 */
	public static void getClusterResult(String pathname) throws Exception{
		Instances instances = null;
		SimpleKMeans KM = null;
		//Ĭ��ŷ����þ���
		DistanceFunction disFun = null;
		//������������
		File file = new File(pathname);
		ArffLoader loader = new ArffLoader();
		loader.setFile(file);
		instances = loader.getDataSet();
		//ɾ����һ�����ԣ�������������id
		instances.deleteAttributeAt(0);
		
		//��ʼ��������,�����㷨
		KM = new SimpleKMeans();
		
		//���ò���
		KM.setNumClusters(20);
		KM.setSeed(10);
		KM.buildClusterer(instances);
		
		//��ӡ���
		//System.out.println(KM.toString());
		
		//���ģ�Ͳ�������	
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
		// ���۾���,��ӡ������
		System.out.println(eval.clusterResultsToString());
		// ���ÿ����¼�����ľ۴� ,�õ�һ��������������
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
