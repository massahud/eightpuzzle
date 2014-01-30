package massahud.ai.solver;

import java.util.ArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SolverTest {

    private static final List<SearchNode<String, String>> EMPTY_LIST = Collections.<SearchNode<String, String>>emptyList();

    @Mock
    private EvaluationFunction<String, String> evaluationFunction;
    @Mock
    private Expander<String, String> expander;
    @Mock
    private GoalEvaluator<String, String> goalEvaluator;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void mustHaveInitialState() {
        final Solver<String, String> solver = new Solver<>();
        solver.solve(null, evaluationFunction, expander, goalEvaluator);
    }

    @Test(timeOut = 1000)
    @SuppressWarnings("unchecked")
    public void shouldReturnInitialStateIfItIsTheSolution() {
        SearchNode<String, String> initialState = new SearchNode<String, String>(null, null, "initial");
        when(evaluationFunction.evaluate(any(SearchNode.class))).thenReturn(0);
        when(goalEvaluator.satisfyGoal(initialState)).thenReturn(true);
        when(expander.expand(any(SearchNode.class))).thenReturn(EMPTY_LIST);

        final Solver<String, String> solver = new Solver<>();
        List<SearchNode<String, String>> solution = solver.solve(initialState, evaluationFunction, expander, goalEvaluator);
        assertThat(solution).hasSize(1).contains(initialState);
    }

    @Test(timeOut = 1000)
    @SuppressWarnings("unchecked")
    public void shouldReturnEmptyListWithNoSolution() {
        when(evaluationFunction.evaluate(any(SearchNode.class))).thenReturn(1);
        when(goalEvaluator.satisfyGoal(any(SearchNode.class))).thenReturn(false);
        when(expander.expand(any(SearchNode.class))).thenReturn(EMPTY_LIST);

        final Solver<String, String> solver = new Solver<>();
        List<SearchNode<String, String>> solution = solver.solve(mock(SearchNode.class), evaluationFunction, expander, goalEvaluator);
        assertThat(solution).isEmpty();
    }

    @Test(timeOut = 1000)
    @SuppressWarnings("unchecked")
    public void shouldReturnTheOnlySolutionPath() {
        SearchNode<String, String> initialState = new SearchNode<String, String>(null, null, "initial");
        SearchNode<String, String> intermediateState1 = new SearchNode<String, String>(initialState, "a", "intermediate1");
        SearchNode<String, String> otherState1 = new SearchNode<String, String>(initialState, "b", "other1");
        SearchNode<String, String> intermediateState2 = new SearchNode<String, String>(intermediateState1, "c", "intermediate2");
        SearchNode<String, String> otherState2 = new SearchNode<String, String>(intermediateState1, "d", "other2");
        SearchNode<String, String> anotherState = new SearchNode<String, String>(otherState1, "e", "another");
        SearchNode<String, String> goalState = new SearchNode<String, String>(intermediateState2, "f", "solution");

        when(evaluationFunction.evaluate(any(SearchNode.class))).thenReturn(1);

        when(goalEvaluator.satisfyGoal(any(SearchNode.class))).thenReturn(false);
        when(goalEvaluator.satisfyGoal(goalState)).thenReturn(true);

        when(expander.expand(any(SearchNode.class))).thenReturn(EMPTY_LIST);
        when(expander.expand(initialState)).thenReturn(Arrays.asList(otherState1, intermediateState1));
        when(expander.expand(intermediateState1)).thenReturn(Arrays.asList(otherState2, intermediateState2));
        when(expander.expand(otherState1)).thenReturn(Arrays.asList(anotherState));
        when(expander.expand(intermediateState2)).thenReturn(Arrays.asList(goalState));

        final Solver<String, String> solver = new Solver<>();
        List<SearchNode<String, String>> solution = solver.solve(initialState, evaluationFunction, expander, goalEvaluator);
        assertThat(solution).containsExactly(initialState, intermediateState1, intermediateState2, goalState);
    }

    @Test(timeOut = 1000)
    @SuppressWarnings("unchecked")
    public void shouldReturnSolutionBasedOnPriorityOfNode1() {
        SearchNode<String, String> initialState = new SearchNode<String, String>(null, null, "initial");
        SearchNode<String, String> intermediateState1 = new SearchNode<String, String>(initialState, "a", "intermediate1");
        SearchNode<String, String> otherState1 = new SearchNode<String, String>(initialState, "b", "other1");
        SearchNode<String, String> intermediateState2 = new SearchNode<String, String>(intermediateState1, "c", "intermediate2");
        SearchNode<String, String> intermediateState3 = new SearchNode<String, String>(intermediateState1, "x", "intermediate3");
        SearchNode<String, String> otherState2 = new SearchNode<String, String>(intermediateState1, "d", "other2");
        SearchNode<String, String> anotherState = new SearchNode<String, String>(otherState1, "e", "another");
        SearchNode<String, String> goalState1 = new SearchNode<String, String>(intermediateState2, "f", "solution");
        SearchNode<String, String> goalState2 = new SearchNode<String, String>(intermediateState3, "y", "solution");

        when(evaluationFunction.evaluate(any(SearchNode.class))).thenReturn(1);

        when(goalEvaluator.satisfyGoal(any(SearchNode.class))).thenReturn(false);
        when(goalEvaluator.satisfyGoal(goalState1)).thenReturn(true);
        when(goalEvaluator.satisfyGoal(goalState2)).thenReturn(true);

        when(expander.expand(any(SearchNode.class))).thenReturn(EMPTY_LIST);
        when(expander.expand(initialState)).thenReturn(new ArrayList<>(Arrays.asList(otherState1, intermediateState1)));
        when(expander.expand(intermediateState1)).thenReturn(new ArrayList<>(Arrays.asList(otherState2, intermediateState2, intermediateState3)));
        when(expander.expand(otherState1)).thenReturn(new ArrayList<>(Arrays.asList(anotherState)));
        when(expander.expand(intermediateState2)).thenReturn(new ArrayList<>(Arrays.asList(goalState1)));
        when(expander.expand(intermediateState3)).thenReturn(new ArrayList<>(Arrays.asList(goalState2)));
        final Solver<String, String> solver = new Solver<>();

        when(evaluationFunction.evaluate(intermediateState2)).thenReturn(4);
        when(evaluationFunction.evaluate(intermediateState3)).thenReturn(5);

        List<SearchNode<String, String>> solution = solver.solve(initialState, evaluationFunction, expander, goalEvaluator);
        assertThat(solution).containsExactly(initialState, intermediateState1, intermediateState2, goalState1);

    }

    @Test(timeOut = 1000)
    @SuppressWarnings("unchecked")
    public void shouldReturnSolutionBasedOnPriorityOfNode2() {

        SearchNode<String, String> initialState = new SearchNode<String, String>(null, null, "initial");
        SearchNode<String, String> intermediateState1 = new SearchNode<String, String>(initialState, "a", "intermediate1");
        SearchNode<String, String> otherState1 = new SearchNode<String, String>(initialState, "b", "other1");
        SearchNode<String, String> intermediateState2 = new SearchNode<String, String>(intermediateState1, "c", "intermediate2");
        SearchNode<String, String> intermediateState3 = new SearchNode<String, String>(intermediateState1, "x", "intermediate3");
        SearchNode<String, String> otherState2 = new SearchNode<String, String>(intermediateState1, "d", "other2");
        SearchNode<String, String> anotherState = new SearchNode<String, String>(otherState1, "e", "another");
        SearchNode<String, String> goalState1 = new SearchNode<String, String>(intermediateState2, "f", "solution");
        SearchNode<String, String> goalState2 = new SearchNode<String, String>(intermediateState3, "y", "solution");

        when(evaluationFunction.evaluate(any(SearchNode.class))).thenReturn(1);

        when(goalEvaluator.satisfyGoal(any(SearchNode.class))).thenReturn(false);
        when(goalEvaluator.satisfyGoal(goalState1)).thenReturn(true);
        when(goalEvaluator.satisfyGoal(goalState2)).thenReturn(true);

        when(expander.expand(any(SearchNode.class))).thenReturn(EMPTY_LIST);
        when(expander.expand(initialState)).thenReturn(new ArrayList<>(Arrays.asList(otherState1, intermediateState1)));
        when(expander.expand(intermediateState1)).thenReturn(new ArrayList<>(Arrays.asList(otherState2, intermediateState2, intermediateState3)));
        when(expander.expand(otherState1)).thenReturn(new ArrayList<>(Arrays.asList(anotherState)));
        when(expander.expand(intermediateState2)).thenReturn(new ArrayList<>(Arrays.asList(goalState1)));
        when(expander.expand(intermediateState3)).thenReturn(new ArrayList<>(Arrays.asList(goalState2)));
        final Solver<String, String> solver = new Solver<>();

        when(evaluationFunction.evaluate(intermediateState2)).thenReturn(5);
        when(evaluationFunction.evaluate(intermediateState3)).thenReturn(4);

        List<SearchNode<String, String>> solution = solver.solve(initialState, evaluationFunction, expander, goalEvaluator);
        assertThat(solution).containsExactly(initialState, intermediateState1, intermediateState3, goalState2);
    }
}
