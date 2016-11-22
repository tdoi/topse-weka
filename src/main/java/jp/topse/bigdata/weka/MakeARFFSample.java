package jp.topse.bigdata.weka;

import java.io.File;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class MakeARFFSample {

    static final String FILE_PATH= "./src/main/resources/sample.arff";
    
    public static void main(String[] args) {
        MakeARFFSample app = new MakeARFFSample();
        app.create(FILE_PATH);
    }
    
    private void create(String filepath) {
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("name", (FastVector)null));

        attributes.addElement(new Attribute("age"));
        
        FastVector sexValues = new FastVector();
        sexValues.addElement("male");
        sexValues.addElement("femail");
        attributes.addElement(new Attribute("sex", sexValues));
        
        Instances data = new Instances("Sample", attributes, 0);
        
        for (int i = 0; i < 10; ++i) {
            double[] values = new double[data.numAttributes()];
            values[0] = data.attribute(0).addStringValue("Taro" + i);
            values[1] = i * 1.5;
            values[2] = data.attribute(2).indexOfValue((i % 3) == 0 ? "femail" : "male");
            data.add(new Instance(1.0, values));
        }

        try {
            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(data);
            arffSaver.setFile(new File(filepath));
            arffSaver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
