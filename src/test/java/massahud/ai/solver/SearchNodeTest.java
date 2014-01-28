package massahud.ai.solver;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchNodeTest {

	@DataProvider
	public Object[][] validConstructionParameters() {
		return new Object[][] { 
				{ "123", null, null },
				{ "asdqwe", "ABCD", mock(SearchNode.class) }
		};
	}
	
	@DataProvider
	public Object[][] invalidConstructionParameters() {
		return new Object[][] { 
				{ null, "asd", mock(SearchNode.class)},
				{ "asdqwe", "ABCD", null },
				{ "asdqwe", null, mock(SearchNode.class) },	
		};
	}

	@Test(dataProvider = "validConstructionParameters")
	public void shouldConstruct(String state, String fromAction,
			SearchNode<String, String> fromNode) {
		final SearchNode<String, String> node = new SearchNode<>(fromNode, fromAction, state);
		assertThat(node.getFromAction()).isEqualTo(fromAction);
		assertThat(node.getFromNode()).isEqualTo(fromNode);
		assertThat(node.getState()).isEqualTo(state);
	}
	
	@Test(dataProvider = "invalidConstructionParameters", expectedExceptions=IllegalArgumentException.class)
	public void shouldNotConstruct(String state, String fromAction,
			SearchNode<String, String> fromNode) {
		final SearchNode<String, String> node = new SearchNode<>(fromNode, fromAction, state);		
	}
	
	@Test
	public void shouldSetCost() {
		final SearchNode<String, String> node = new SearchNode<>(mock(SearchNode.class),"action","state");
		assertThat(node.getCost()).isNull();
		node.setCost(Integer.MAX_VALUE);
		assertThat(node.getCost()).isEqualTo(Integer.MAX_VALUE);
		node.setCost(Integer.MIN_VALUE);
		assertThat(node.getCost()).isEqualTo(Integer.MIN_VALUE);
		node.setCost(0);
		assertThat(node.getCost()).isEqualTo(0);
	}

}
