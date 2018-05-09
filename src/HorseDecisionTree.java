import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author John Brockway
 */
public class HorseDecisionTree {

	public static void main(String[] args) {
		try {
			ArrayList<Case> trainingSet = new ArrayList<Case>();
			BufferedReader input = new BufferedReader(new FileReader("../horseTrain.txt"));
			String line = input.readLine();
			while (line != null) {
				String[] lineSplit = line.split(",");
				trainingSet.add(new Case(Double.parseDouble(lineSplit[0]),
						Double.parseDouble(lineSplit[1]),
						Double.parseDouble(lineSplit[2]),
						Double.parseDouble(lineSplit[3]),
						Double.parseDouble(lineSplit[4]),
						Double.parseDouble(lineSplit[5]),
						Double.parseDouble(lineSplit[6]),
						Double.parseDouble(lineSplit[7]),
						Double.parseDouble(lineSplit[8]),
						Double.parseDouble(lineSplit[9]),
						Double.parseDouble(lineSplit[10]),
						Double.parseDouble(lineSplit[11]),
						Double.parseDouble(lineSplit[12]),
						Double.parseDouble(lineSplit[13]),
						Double.parseDouble(lineSplit[14]),
						Double.parseDouble(lineSplit[15]),
						lineSplit[16].equals("healthy.")));

				line = input.readLine();
			}
			input.close();
			
			ArrayList<Case> testSet = new ArrayList<Case>();
			BufferedReader inputTest = new BufferedReader(new FileReader("../horseTest.txt"));
			line = inputTest.readLine();
			while (line != null) {
				String[] lineSplit = line.split(",");
				testSet.add(new Case(Double.parseDouble(lineSplit[0]),
						Double.parseDouble(lineSplit[1]),
						Double.parseDouble(lineSplit[2]),
						Double.parseDouble(lineSplit[3]),
						Double.parseDouble(lineSplit[4]),
						Double.parseDouble(lineSplit[5]),
						Double.parseDouble(lineSplit[6]),
						Double.parseDouble(lineSplit[7]),
						Double.parseDouble(lineSplit[8]),
						Double.parseDouble(lineSplit[9]),
						Double.parseDouble(lineSplit[10]),
						Double.parseDouble(lineSplit[11]),
						Double.parseDouble(lineSplit[12]),
						Double.parseDouble(lineSplit[13]),
						Double.parseDouble(lineSplit[14]),
						Double.parseDouble(lineSplit[15]),
						lineSplit[16].equals("healthy.")));

				line = inputTest.readLine();
			}
			inputTest.close();
			
			Node decisionTree = buildTree(trainingSet);
			
			int testSize = testSet.size();
			int correctlyClassified = 0;
			for (Case c : testSet) {
				if (testTree(c, decisionTree)) correctlyClassified++;
			}
			System.out.println("Classification Rate on Test Set: \t" + correctlyClassified + "/" + testSize);
			
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	/**
	 * Classifies a case using the decision tree, and then checks the tree's classification against the true classification
	 * @param c The case to classify
	 * @param tree The decision tree to be used
	 * @return Whether the tree correctly classified the case or not
	 */
	public static boolean testTree(Case c, Node tree) {
		if (tree.isLeaf) {
			// If this is a result node, return whether the tree result matches the true classification
			return c.isHealthy == tree.isHealthy;
		}
		else {
			// Else, we find the appropriate value, and then compare that to the threshold to recurse on the correct subtree
			double value = 0;
			switch (tree.attribute) {
			case K:
				value = c.k;
				break;
			case Na:
				value = c.na;
				break;
			case CL:
				value = c.cl;
				break;
			case HCO3:
				value = c.hco3;
				break;
			case Endotoxin:
				value = c.endotoxin;
				break;
			case Aniongap:
				value = c.aniongap;
				break;
			case PLA2:
				value = c.pla2;
				break;
			case SDH:
				value = c.sdh;
				break;
			case GLDH:
				value = c.gldh;
				break;
			case TPP:
				value = c.tpp;
				break;
			case BreathRate:
				value = c.breathRate;
				break;
			case PCV:
				value = c.pcv;
				break;
			case PulseRate:
				value = c.pulseRate;
				break;
			case Fibrinogen:
				value = c.fibrinogen;
				break;
			case Dimer:
				value = c.dimer;
				break;
			case FibPerDim:
				value = c.fibPerDim;
				break;
			}
			// Recursion
			if (value < tree.threshold) {
				return testTree(c, tree.lessThanChildren);
			}
			else {
				return testTree(c, tree.greaterThanChildren);
			}
		}
	}
	
	/**
	 * Builds the decision tree based on a training set
	 * @param cases The set of cases to train on
	 * @return A decision tree for the Healthiness classification problem based on the training set
	 */
	public static Node buildTree(ArrayList<Case> cases) {
		int healthy = 0;
		// Count the number of healthy cases in the current set
		for (Case c : cases) {
			if (c.isHealthy) healthy++;
		}
		// If all cases are either healthy or unhealthy, this can be a result node and so is set as such (to the correct value)
		if (healthy == cases.size() || healthy == 0) {
			return new Node(cases.get(0).isHealthy);
		}
		else {
			// Otherwise we have a mix, so find the best attribute and matching threshold to split on
			Attribute attribute = chooseAttribute(cases);
			double threshold = chooseAttributeThreshold(cases, attribute);
			Node tree = new Node();
			tree.attribute = attribute;
			tree.threshold = threshold;
			ArrayList<Case> casesLessThan = new ArrayList<Case>();
			ArrayList<Case> casesGreaterThan = new ArrayList<Case>();
			// Get the appropriate value and use that to sort into two lists, one for each side of the threshold
			for (Case c : cases) {
				double value = 0;
				switch (attribute) {
				case K:
					value = c.k;
					break;
				case Na:
					value = c.na;
					break;
				case CL:
					value = c.cl;
					break;
				case HCO3:
					value = c.hco3;
					break;
				case Endotoxin:
					value = c.endotoxin;
					break;
				case Aniongap:
					value = c.aniongap;
					break;
				case PLA2:
					value = c.pla2;
					break;
				case SDH:
					value = c.sdh;
					break;
				case GLDH:
					value = c.gldh;
					break;
				case TPP:
					value = c.tpp;
					break;
				case BreathRate:
					value = c.breathRate;
					break;
				case PCV:
					value = c.pcv;
					break;
				case PulseRate:
					value = c.pulseRate;
					break;
				case Fibrinogen:
					value = c.fibrinogen;
					break;
				case Dimer:
					value = c.dimer;
					break;
				case FibPerDim:
					value = c.fibPerDim;
					break;
				}
				if (value < threshold) {
					casesLessThan.add(c);
				}
				else {
					casesGreaterThan.add(c);
				}
			}
			// Recurse on these sets to create subtrees
			tree.lessThanChildren = buildTree(casesLessThan);
			tree.greaterThanChildren = buildTree(casesGreaterThan);
			
			return tree;
		}
	}

	/**
	 * The Information Content function I(p/p+n, n/p+n) from lecture
	 * @param p The number of positive examples in the set
	 * @param n The number of negative examples in the set
	 * @return The Information Content as specified in lecture notes 17-18 slide 30
	 */
	public static double informationContent(double p, double n) {
		double pn = p + n;
		// Modified so that log(0) is treated as 0
		if (p == 0) {
			return ((-p/pn) * 0) - ((n/pn) * (Math.log(n/pn)/Math.log(2)));
		}
		else if (n == 0) {
			return ((-p/pn) * (Math.log(p/pn)/Math.log(2))) - ((n/pn) * 0);
		}
		else {
			return ((-p/pn) * (Math.log(p/pn)/Math.log(2))) - ((n/pn) * (Math.log(n/pn)/Math.log(2)));
		}
	}

	/**
	 * Based on a set of Cases, finds the attribute that gives the highest information gain
	 * This method is fairly ugly, it could definitely be cut down on in length as there is a lot of repeated code,
	 * but it is perfectly functional in its current state, just a lot of repetition
	 * @param cases The current subset of the training set
	 * @return The attribute with the highest information gain
	 */
	public static Attribute chooseAttribute(ArrayList<Case> cases) {
		Attribute currentBestAttribute = null;
		double currentBestRemainder = 0;

		// k
		double [] kValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			// Extract the correct fields from each case into a separate array for sorting
			kValues[i] = cases.get(i).k;
		}
		Arrays.sort(kValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			// Find the midpoint between two sequential values
			double midpoint = (kValues[i] + kValues[i+1]) / 2;
			// A rough way of dealing with non-unique values; two sequential identical values' average will be themselves, so ignore anything coming from such a pairing
			if (midpoint == kValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				// Count the number of positive and negative cases that exist on either side of the threshold
				if (c.k < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			// Calculate the remainder as given in lecture slides 17-18 slide 31
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			// We want the attribute that gives the highest information gain, and since information content of the full
			// set is constant, this is equivalent to the smallest remainder
			if (remainder < currentBestRemainder || currentBestAttribute == null) {
				currentBestAttribute = Attribute.K;
				currentBestRemainder = remainder;
			}
		}

		// na
		double [] naValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			naValues[i] = cases.get(i).na;
		}
		Arrays.sort(naValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (naValues[i] + naValues[i+1]) / 2;
			if (midpoint == naValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.na < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.Na;
				currentBestRemainder = remainder;
			}
		}

		// cl
		double [] clValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			clValues[i] = cases.get(i).cl;
		}
		Arrays.sort(clValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (clValues[i] + clValues[i+1]) / 2;
			if (midpoint == clValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.cl < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.CL;
				currentBestRemainder = remainder;
			}
		}

		// hco3
		double [] hco3Values = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			hco3Values[i] = cases.get(i).hco3;
		}
		Arrays.sort(hco3Values);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (hco3Values[i] + hco3Values[i+1]) / 2;
			if (midpoint == hco3Values[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.hco3 < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.HCO3;
				currentBestRemainder = remainder;
			}
		}

		// endotoxin
		double [] endotoxinValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			endotoxinValues[i] = cases.get(i).endotoxin;
		}
		Arrays.sort(endotoxinValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (endotoxinValues[i] + endotoxinValues[i+1]) / 2;
			if (midpoint == endotoxinValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.endotoxin < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.Endotoxin;
				currentBestRemainder = remainder;
			}
		}

		// aniongap
		double [] aniongapValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			aniongapValues[i] = cases.get(i).aniongap;
		}
		Arrays.sort(aniongapValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (aniongapValues[i] + aniongapValues[i+1]) / 2;
			if (midpoint == aniongapValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.aniongap < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.Aniongap;
				currentBestRemainder = remainder;
			}
		}

		// pla2
		double [] pla2Values = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			pla2Values[i] = cases.get(i).pla2;
		}
		Arrays.sort(pla2Values);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (pla2Values[i] + pla2Values[i+1]) / 2;
			if (midpoint == pla2Values[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.pla2 < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.PLA2;
				currentBestRemainder = remainder;
			}
		}

		// sdh
		double [] sdhValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			sdhValues[i] = cases.get(i).sdh;
		}
		Arrays.sort(sdhValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (sdhValues[i] + sdhValues[i+1]) / 2;
			if (midpoint == sdhValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.sdh < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.SDH;
				currentBestRemainder = remainder;
			}
		}

		// gldh
		double [] gldhValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			gldhValues[i] = cases.get(i).gldh;
		}
		Arrays.sort(gldhValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (gldhValues[i] + gldhValues[i+1]) / 2;
			if (midpoint == gldhValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.gldh < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.GLDH;
				currentBestRemainder = remainder;
			}
		}

		// tpp
		double [] tppValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			tppValues[i] = cases.get(i).tpp;
		}
		Arrays.sort(tppValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (tppValues[i] + tppValues[i+1]) / 2;
			if (midpoint == tppValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.tpp < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.TPP;
				currentBestRemainder = remainder;
			}
		}

		// breathRate
		double [] breathRateValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			breathRateValues[i] = cases.get(i).breathRate;
		}
		Arrays.sort(breathRateValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (breathRateValues[i] + breathRateValues[i+1]) / 2;
			if (midpoint == breathRateValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.breathRate < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.BreathRate;
				currentBestRemainder = remainder;
			}
		}

		// pcv
		double [] pcvValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			pcvValues[i] = cases.get(i).pcv;
		}
		Arrays.sort(pcvValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (pcvValues[i] + pcvValues[i+1]) / 2;
			if (midpoint == pcvValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.pcv < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.PCV;
				currentBestRemainder = remainder;
			}
		}

		// pulseRate
		double [] pulseRateValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			pulseRateValues[i] = cases.get(i).pulseRate;
		}
		Arrays.sort(pulseRateValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (pulseRateValues[i] + pulseRateValues[i+1]) / 2;
			if (midpoint == pulseRateValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.pulseRate < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.PulseRate;
				currentBestRemainder = remainder;
			}
		}

		// fibrinogen
		double [] fibrinogenValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			fibrinogenValues[i] = cases.get(i).fibrinogen;
		}
		Arrays.sort(fibrinogenValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (fibrinogenValues[i] + fibrinogenValues[i+1]) / 2;
			if (midpoint == fibrinogenValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.fibrinogen < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.Fibrinogen;
				currentBestRemainder = remainder;
			}
		}

		// dimer
		double [] dimerValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			dimerValues[i] = cases.get(i).dimer;
		}
		Arrays.sort(dimerValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (dimerValues[i] + dimerValues[i+1]) / 2;
			if (midpoint == dimerValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.dimer < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.Dimer;
				currentBestRemainder = remainder;
			}
		}

		// fibPerDim
		double [] fibPerDimValues = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			fibPerDimValues[i] = cases.get(i).fibPerDim;
		}
		Arrays.sort(fibPerDimValues);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (fibPerDimValues[i] + fibPerDimValues[i+1]) / 2;
			if (midpoint == fibPerDimValues[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				if (c.fibPerDim < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			if (remainder < currentBestRemainder) {
				currentBestAttribute = Attribute.FibPerDim;
				currentBestRemainder = remainder;
			}
		}

		return currentBestAttribute;
	}

	/**
	 * Based on a set of Cases and an attribute, finds the threshold for that attribute that gives the highest information gain
	 * @param cases The current subset of the training set
	 * @return The attribute with the highest information gain
	 * @param attribute The attribute with the highest information gain
	 * @return The threshold with the highest information gain for the given attribute
	 */
	public static double chooseAttributeThreshold(ArrayList<Case> cases, Attribute attribute) {
		double currentBestThreshold = -1;
		double currentBestRemainder = 0;

		double [] values = new double[cases.size()];
		for (int i = 0 ; i < cases.size() ; i++) {
			// Create an array of the appropriate field values from each case for easy sorting
			switch (attribute) {
			case K:
				values[i] = cases.get(i).k;
				break;
			case Na:
				values[i] = cases.get(i).na;
				break;
			case CL:
				values[i] = cases.get(i).cl;
				break;
			case HCO3:
				values[i] = cases.get(i).hco3;
				break;
			case Endotoxin:
				values[i] = cases.get(i).endotoxin;
				break;
			case Aniongap:
				values[i] = cases.get(i).aniongap;
				break;
			case PLA2:
				values[i] = cases.get(i).pla2;
				break;
			case SDH:
				values[i] = cases.get(i).sdh;
				break;
			case GLDH:
				values[i] = cases.get(i).gldh;
				break;
			case TPP:
				values[i] = cases.get(i).tpp;
				break;
			case BreathRate:
				values[i] = cases.get(i).breathRate;
				break;
			case PCV:
				values[i] = cases.get(i).pcv;
				break;
			case PulseRate:
				values[i] = cases.get(i).pulseRate;
				break;
			case Fibrinogen:
				values[i] = cases.get(i).fibrinogen;
				break;
			case Dimer:
				values[i] = cases.get(i).dimer;
				break;
			case FibPerDim:
				values[i] = cases.get(i).fibPerDim;
				break;
			}
		}
		Arrays.sort(values);
		for (int i = 0 ; i < cases.size() - 1 ; i++) {
			double midpoint = (values[i] + values[i+1]) / 2;
			// A rough way of dealing with non-unique values; two sequential identical values' average will be themselves, so ignore anything coming from such a pairing
			if (midpoint == values[i]) continue;
			double pless = 0;
			double nless = 0;
			double pgreater = 0;
			double ngreater = 0;
			for (Case c : cases) {
				// Count the number of positive and negative cases that exist on either side of the threshold
				double value = 0;
				switch (attribute) {
				case K:
					value = c.k;
					break;
				case Na:
					value = c.na;
					break;
				case CL:
					value = c.cl;
					break;
				case HCO3:
					value = c.hco3;
					break;
				case Endotoxin:
					value = c.endotoxin;
					break;
				case Aniongap:
					value = c.aniongap;
					break;
				case PLA2:
					value = c.pla2;
					break;
				case SDH:
					value = c.sdh;
					break;
				case GLDH:
					value = c.gldh;
					break;
				case TPP:
					value = c.tpp;
					break;
				case BreathRate:
					value = c.breathRate;
					break;
				case PCV:
					value = c.pcv;
					break;
				case PulseRate:
					value = c.pulseRate;
					break;
				case Fibrinogen:
					value = c.fibrinogen;
					break;
				case Dimer:
					value = c.dimer;
					break;
				case FibPerDim:
					value = c.fibPerDim;
					break;
				}
				if (value < midpoint) {
					if (c.isHealthy) pless++;
					else nless++;
				}
				else {
					if (c.isHealthy) pgreater++;
					else ngreater++;
				}
			}
			// Calculate the remainder as defined in lecture notes 17-18 slide 31
			double remainder = ((pless+nless)/cases.size() * informationContent(pless, nless))
					+ ((pgreater+ngreater)/cases.size() * informationContent(pgreater, ngreater));
			// We want the attribute that gives the highest information gain, and since information content of the full
			// set is constant, this is equivalent to the smallest remainder
			if (remainder < currentBestRemainder || currentBestThreshold == -1) {
				currentBestThreshold = midpoint;
				currentBestRemainder = remainder;
			}
		}

		return currentBestThreshold;
	}

}