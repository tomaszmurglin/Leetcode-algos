package com.murglin.consulting.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // write your code here
        final var main = new Main();
        final var solution = main.new Solution();
        var result = solution.canCompleteCircuit(new int[] {4,5,3,1,4}, new int[] {5,4,3,4,2});
        System.out.println(result);
    }

    class Solution {
        public int[] findIndiciesWhichValuesSumToTargetValue(int[] nums, int target) {
            int[] indicies = new int[2];
            for (int i = 0; i < nums.length; i++) {
                int currentNumber = nums[i];
                for (int counter = i + 1; counter <= nums.length - 1; counter++) {
                    int nextNumber = nums[counter];
                    int sum = currentNumber + nextNumber;
                    if (sum == target) {
                        indicies[0] = i;
                        indicies[1] = counter;
                    }
                }
            }
            return indicies;
        }

        public int lengthOfLongestSubstring(String s) {
            if (s.length() <= 1) {
                return s.length();
            }
            var numberOfChars = s.length();
            final var chars = s.toCharArray();
            final var results = new TreeSet<Integer>();
            for (int i = 0; i < numberOfChars; i++) {
                var traversedChars = new HashSet<>();
                var previousChar = chars[i];
                traversedChars.add(previousChar);
                for (int counter = i + 1; counter <= numberOfChars - 1; counter++) {
                    final var nextChar = chars[counter];
                    var isRrepeatedChar = traversedChars.add(nextChar);
                    if (!isRrepeatedChar) {
                        break;
                    }
                }
                results.add(traversedChars.size());
            }
            return results.last();
        }

        public int canCompleteCircuit(int[] gas, int[] cost) {
            var numberOfGasStations = gas.length;
            final var gasStationsFromArray = Arrays.stream(gas).boxed().collect(Collectors.toList());
            final var costsFromArray = Arrays.stream(cost).boxed().collect(Collectors.toList());
            for (int index = 0; index < numberOfGasStations; index++) {
                var amountOfGasInTheGasTankAfterTravelToNextStation = 0;
                final var gasStations = new ArrayList<>(gasStationsFromArray);
                Collections.rotate(gasStations, -index);
                final var costs = new ArrayList<>(costsFromArray);
                Collections.rotate(costs, -index);
                int gasStationsVisited = 1;
                for (int i = 0; i < numberOfGasStations; i++) {
                    var amountOfGasTanked = gasStations.get(i);
                    var numberOfGasBurnDuringTravelToNextStation = costs.get(i);
                    amountOfGasInTheGasTankAfterTravelToNextStation =
                        amountOfGasInTheGasTankAfterTravelToNextStation + amountOfGasTanked - numberOfGasBurnDuringTravelToNextStation;
                    var isTravelToLastGasStation = gasStationsVisited == numberOfGasStations;
                    if (amountOfGasInTheGasTankAfterTravelToNextStation <= 0 && !isTravelToLastGasStation) {
                        break;
                    }
                    if(amountOfGasInTheGasTankAfterTravelToNextStation >= 0){
                        gasStationsVisited++;
                    }
                    var isLastGasStation = gasStationsVisited == numberOfGasStations + 1;
                    if (isLastGasStation) {
                        return index;
                    }
                }
            }
            return -1;
        }

    }
}
