import view.Core;
import view.screen.Development;
import view.screen.News;
import view.screen.Nexus;
import view.screen.Research;
import view.screen.Title;

public class BlackBoxTechnology {
	
	private final static int width = 600;
	private final static int height = 400;

	public static void main(String[] args) {
		Core core = new Core(600, 400);
		Title titlePanel = new Title(0, 0, width, height, core, "title");
		Nexus nexus = new Nexus(0, 0, width, height, core, "nexus");
		News news = new News(0, 0, width, height, core, "news");
		Research research = new Research(0, 0, width, height, core, "research");
		Development development = new Development(0, 0, width, height, core, "development");
		core.addScreen(titlePanel);
		core.addScreen(nexus);
		core.addScreen(news);
		core.addScreen(research);
		core.addScreen(development);
		core.setActive(titlePanel);
	}
	
}
