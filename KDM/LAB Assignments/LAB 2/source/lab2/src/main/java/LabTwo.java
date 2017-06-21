

import java.io.*;
import java.util.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;


public class LabTwo implements Serializable{

    Multimap<String, String> mm = HashMultimap.create();

    public String lemtzn(String n) {

        StringBuilder sbuild = new StringBuilder();
        Properties propts = new Properties();


        propts.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipelin = new StanfordCoreNLP(propts);
        Annotation anote = new Annotation(n);

        pipelin.annotate(anote);

        List<CoreMap> lc = anote.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : lc) {
            for (CoreLabel token2 : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String s = token2.get(CoreAnnotations.LemmaAnnotation.class);
                sbuild.append(s + " ");
            }
        }
        return sbuild.toString();
    }
    public void namedEntyRltns(String ss)
    {
        Properties p1=new Properties();
        p1.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline1 = new StanfordCoreNLP(p1);
        Annotation a1 = new Annotation(ss);
        pipeline1.annotate(a1);
        List<CoreMap> li=a1.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap line:li){
            for(CoreLabel t:line.get(CoreAnnotations.TokensAnnotation.class))
            {
                String ner1=t.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String w1=t.get(CoreAnnotations.TextAnnotation.class);
                mm.put(ner1, w1);

            }
        }
    }
    public String retrn(String a,String b)
    {
        namedEntyRltns(a);
        Collection<String> a1=mm.get(b);

        StringBuilder build=new StringBuilder();
        for(String element:a1)
        {
            build.append(element+" ");
        }
        System.out.println(build.toString());
        return build.toString();
    }
   }
