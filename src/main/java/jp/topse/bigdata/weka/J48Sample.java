package jp.topse.bigdata.weka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class J48Sample {
    
    private static final String ARFF_PATH= "src/main/resources/iris.arff";
    
    public static void main(String[] args) {
        J48Sample app = new J48Sample();
        app.analyze(ARFF_PATH);
    }
    
    private void analyze(String arffPath) {
        try {
            Instances data = loadData(arffPath);
            if (data == null) {
                return;
            }
            
            J48 tree = new J48();
            String[] options = new String[1];
            options[0] = "-U";
            tree.setOptions(options);
            tree.buildClassifier(data);
            
            evalResult(tree, data);
            
            showResult(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Instances loadData(String arffPath) {
        try {
            Instances data = new Instances(new BufferedReader(new FileReader(arffPath)));
            data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void evalResult(J48 tree, Instances data) {
        try {
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(tree,  data, 10, new Random(1));
            System.out.println(eval.toSummaryString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showResult(J48 tree) {
        try {
            TreeVisualizer visualizer = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
            
            JFrame frame = new JFrame("Results");
            frame.setSize(800, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            frame.getContentPane().add(visualizer);
            frame.setVisible(true);
            visualizer.fitToScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
