Name: Anmol Chhabria
GTID: 902931430

Running Algorithms
________________
For backpropagation, I used Weka to run the Multilayer Perceptron over different epochs for Part 1. Used Weka to generate respective train and test error for epochs.

In order to run all the algorithms I used ABAGAIL. Download ABAGAIL, ANT. To run the code:
1.Save all files from the Code.zip folder into the test folder in ABAGAIL (ABAGAIL-src-opt-test)
2.Command Line instructions:
-cd ABAGAIL
-ant
-java -cp ABAGAIL.jar opt.test.[className]
__________________

Files
__________________

Achhabria7-analysis.pdf : Analysis for assignment
Dataset: EEG_Data.csv
Analysis_Data: Excel with data for Part 1 (Backpropagation) and Continuous Peaks Analysis
Analysis_TravelingSalesman: TSP Analysis
Analysis_Knapsack: Knapsack Analysis
Code.zip: 
Part1
-EEGTest2.java 
Part2
-KnapsackTest.java
-KnapsackTestIterations.java
-ContinuousPeaksTest.java
-ContinuousPeaksTestIterations.java
-TravelingSalesmanTest.java
-TravelingSalesmanTestIterations.java

______________________

EEG Data Description
______________________

References:

Wang, H., Li, Y., Hu, X., Yang, Y., Meng, Z., & Chang, K. M. (2013, June). Using EEG to Improve Massive Open Online Courses Feedback Interaction. In AIED Workshops.
Wang, H., & Yang, J. (2016). Multiple Confounders Correction with Regularized Linear Mixed Effect Models, with Application in Biological Processes. Bioinformatics and Biomedicine (BIBM)
Yang, J., Wang, H., Zhu, J., & Xing, E. P. (2016). SeDMiD for Confusion Detection: Uncovering Mind State from Time Series Brain Wave Data. NIPS 2016 Timg Series Workshop

Website: https://www.kaggle.com/wanghaohan/eeg-brain-wave-for-confusion

Description: We collected EEG signal data from 10 college students while they watched MOOC video clips. We extracted online education videos that are assumed not to be confusing for college students, such as videos of the introduction of basic algebra or geometry. We also prepare videos that are expected to confuse a typical college student if a student is not familiar with the video topics like Quantum Mechanics, and Stem Cell Research. 

Attributes
	1	Column 1: Subject ID
	2	Column 2: Video ID
	3	Column 3: Attention (Proprietary measure of mental focus)
	4	Column 4: Mediation (Proprietary measure of calmness)
	5	Column 5: Raw (Raw EEG signal)
	6	Column 6: Delta (1-3 Hz of power spectrum)
	7	Column 7: Theta (4-7 Hz of power spectrum)
	8	Column 8: Alpha 1 (Lower 8-11 Hz of power spectrum)
	9	Column 9: Alpha 2 (Higher 8-11 Hz of power spectrum)
	10	Column 10: Beta 1 (Lower 12-29 Hz of power spectrum)
	11	Column 11: Beta 2 (Higher 12-29 Hz of power spectrum)
	12	Column 12: Gamma 1 (Lower 30-100 Hz of power spectrum)
	13	Column 13: Gamma 2 (Higher 30-100 Hz of power spectrum)
	14	Column 14: predefined label (whether the subject is expected to be confused) - NOT USED this attribute IN MY ANALYSIS
	15	Column 15: user-defined label (whether the subject is actually confused)