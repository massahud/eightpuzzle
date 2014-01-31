/*
 * The MIT License
 *
 * Copyright 2014 Geraldo Massahud.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
