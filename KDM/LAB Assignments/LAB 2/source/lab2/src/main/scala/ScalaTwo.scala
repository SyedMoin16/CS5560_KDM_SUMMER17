import org.apache.spark.{SparkConf, SparkContext}


object ScalaTwo {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "F:\\summer\\kdm");

    val sparkConf = new SparkConf().setAppName("SparkTransformations").setMaster("local[*]")

    val sc1=new SparkContext(sparkConf)


    val call:LabTwo=new LabTwo();
    val text=sc1.textFile("C:\\Users\\syedm\\Desktop\\kdm\\001.txt");
    val txt1=text.map(l=>{call.lemtzn(l)})

    val txt2=txt1.flatMap(a=>{a.split(" ")}).filter(b=>(!(b.contains(",")|b.contains(".")|(b.isEmpty)))).map(c=>(c))
    val txt3=txt2.groupBy(g1=>{g1.charAt(0)})
    txt3.collect().foreach(println)

  }

}
