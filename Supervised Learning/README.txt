Name: Anmol Chhabria
GTID: 902931430

Running Algorithms
________________

In order to run all the algorithms I used Weka. The following classifiers were run in Weka Explorer:
-J48 (pruned and unpruned) for decision trees. Hyperparameters: Confidence factor
-IBk for K-NN. Hyperparameters: Cluster K
-AdaBoost1 for Boosting. Hyperparameters: Number of Iterations.
-SMO (Poly and Puk Kernels) for support vector machines. Hyperparameters: kernel exponent(Poly), omega/sigma(Puk)
-Multilayer Perceptron for artificial neural networks. Hyperparameters: momentum, learning rate
__________________

Files
__________________

Achhabria7-analysis.pdf : Analysis for assignment
Split_MOOC.zip: Data split for running learning curves for MOOC EEG data manually in Weka
Split_Movies.zip: Data split for running learning curves for Movies data manually in Weka
movie_metadata.csv: Data for Movies from Kaggle
EEG data.csv: Data for EEG MOOC from Kaggle
Analysis_Results: Excel file with Raw inputs, tables, graphs
__________________

Movies Data Description
____________________

Website: https://www.kaggle.com/deepmatrix/imdb-5000-movie-dataset
Attributes: There are 2399 unique director names, and thousands of actors/actresses. Below are the 28 variables: "movie_title" "color" "num_critic_for_reviews" "movie_facebook_likes" "duration" "director_name" "director_facebook_likes" "actor_3_name" "actor_3_facebook_likes" "actor_2_name" "actor_2_facebook_likes" "actor_1_name" "actor_1_facebook_likes" "gross" "genres" "num_voted_users" "cast_total_facebook_likes" "facenumber_in_poster" "plot_keywords" "movie_imdb_link" "num_user_for_reviews" "language" "country" "content_rating" "budget" "title_year" "imdb_score" "aspect_ratio"
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
	14	Column 14: predefined label (whether the subject is expected to be confused)
	15	Column 15: user-defined label (whether the subject is actually confused)