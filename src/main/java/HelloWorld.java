import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class HelloWorld implements SparkApplication {
	public static void main(String[] args) {
		new HelloWorld().init();
	}

	@Override
	public void init() {
		get("/hello", (req, res) -> "<html><center><font><B>Sky Devops Implementation</B></font></center></html>");
		//get("/hello", (req, res) -> "<html><center><font size="55">Sky Devops Implementation</font></center></html>");
	}
}
