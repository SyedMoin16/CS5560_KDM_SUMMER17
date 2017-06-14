

import java.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class TestCoreNLP {

    public static void main(String[] args) throws IOException {
        PrintWriter out;
        out = new PrintWriter("C:\\Users\\syedm\\Desktop\\kdm\\output1.txt");

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos,lemma,ner,parse,dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation;
        String readString = null;
        PrintWriter pw = null;
        BufferedReader br = null;
        br = new BufferedReader(new FileReader("C:\\Users\\syedm\\Desktop\\kdm\\001.txt"));
        pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\syedm\\Desktop\\kdm\\output.txt", false)));
        String x = null;

        int i=0;
        System.out.println("What is the Organization?");
        while ((readString = br.readLine()) != null && i<5) {
            pw.println(readString);
            String xx = readString;
            x = xx;
            annotation = new Annotation(x);

            pipeline.annotate(annotation);
            pipeline.prettyPrint(annotation, out);

            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);


//                        if (ne.contains("PERSON")) {
//
//                            //System.out.println("Player Name" + token);
//                            String word = token.get(CoreAnnotations.TextAnnotation.class);
//
//                            System.out.println( "Player Name:" + word);
//                        }
//                        //else
//                            if (ne.contains("LOCATION")) {
//                           // System.out.println("Where is the location?");
//                            System.out.println("Location :" + token);
//                        }
                       // else
                    if (ne.contains("ORGANIZATION")) {
//                            System.out.println("What is the Organisaion?");
                         System.out.println("ORGANIZATION :" + token);
                        }
                    }


            }
        }
            br.close();
            pw.close();
            System.out.println("NLP");

//            try {
//                BufferedReader b = new BufferedReader(new FileReader("C:\\Users\\syedm\\Desktop\\kdm\\output1.txt"));
//                String line = null;
//                int i = 0;
//                while ((line = b.readLine()) != null && i < 10) {
//                    System.out.println(line);
//                    i++;
//                }
//                b.close();
//            } catch (Exception e) {
//            }





    }}