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
        var result = solution.findMinNumberOfCoinsToInverse(new int[] {1, 1, 0, 1, 1});
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
                        amountOfGasInTheGasTankAfterTravelToNextStation + amountOfGasTanked -
                            numberOfGasBurnDuringTravelToNextStation;
                    var isTravelToLastGasStation = gasStationsVisited == numberOfGasStations;
                    if (amountOfGasInTheGasTankAfterTravelToNextStation <= 0 && !isTravelToLastGasStation) {
                        break;
                    }
                    if (amountOfGasInTheGasTankAfterTravelToNextStation >= 0) {
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


        public int findMinNumberOfCoinsToInverse(int[] A) {
            // write your code in Java SE 8
            final int[] coins = A; //A name was not meaningful
            final int coinsQuantity = coins.length;

            validate(coins, coinsQuantity);

            if (coinsQuantity == 1) {
                return 0;
            }
            int quantityOfCoinsToInverse = countCoinsToInverse(coins);

            int firstCoin = coins[0];
            int inversedFirstCoin = firstCoin == 0 ? 1 : 0;
            coins[0] = inversedFirstCoin;
            int alternativeQuantityOfCoinsToInverse = countCoinsToInverse(coins);

            return Math.min(quantityOfCoinsToInverse, alternativeQuantityOfCoinsToInverse);
        }

        private void validate(int[] A, final int coinsQuantity) {
            if (coinsQuantity < 1 || coinsQuantity > 100) {
                throw new IllegalArgumentException("Coins quantity doesnt match expected value");
            }
            final long filteredCoinsQuantity = Arrays.stream(A).filter(coin -> coin == 0 || coin == 1).count();
            if (filteredCoinsQuantity < coinsQuantity) {
                throw new IllegalArgumentException("Coins should have only value 0 or 1");
            }
        }

        private int countCoinsToInverse(int[] coins) {
            int firstCoin = coins[0];
            int inversedFirstCoin = firstCoin == 0 ? 1 : 0;
            int quantityOfCoinsToInverse = 0;
            for (int i = 0; i < coins.length; i++) {
                boolean isOddNumberOfCoin = i % 2 == 1;
                if (isOddNumberOfCoin) {
                    if (coins[i] != inversedFirstCoin) {
                        quantityOfCoinsToInverse++;
                    }
                } else {
                    if (coins[i] != firstCoin) {
                        quantityOfCoinsToInverse++;
                    }
                }
            }
            return quantityOfCoinsToInverse;
        }
    }
}
