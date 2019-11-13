import view.Visual;
import view.screen.Development;
import view.screen.News;
import view.screen.Nexus;
import view.screen.Research;
import view.screen.Title;

public class BlackBoxTechnology {
	
	private final static int width = 600;
	private final static int height = 400;

	public static void main(String[] args) {
		Visual Visual = new Visual(600, 400);
		Title titlePanel = new Title(0, 0, width, height, Visual, "title");
		Nexus nexus = new Nexus(0, 0, width, height, Visual, "nexus");
		News news = new News(0, 0, width, height, Visual, "news");
		Research research = new Research(0, 0, width, height, Visual, "research");
		Development development = new Development(0, 0, width, height, Visual, "development");
		Visual.addScreen(titlePanel);
		Visual.addScreen(nexus);
		Visual.addScreen(news);
		Visual.addScreen(research);
		Visual.addScreen(development);
		Visual.setActive(titlePanel);
	}
	
}
