using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

/// <summary>
/// Script to handle end screen results
/// </summary>
public class EndScreen : MonoBehaviour
{
    TextMeshProUGUI levelText;
    TextMeshProUGUI timerText;
    TextMeshProUGUI scoreText;
    int[] results;

    DBResultsManager db;

    /// <summary>
    /// Displays the results
    /// </summary>
    public void Start()
    {
        db = GetComponent<DBResultsManager>();

        results = new int[9];
        GameObject qnContainer = GameObject.Find("StatsPanel/QuestionContainer");
        for (int i = 0; i < 3; i++)
        {
            string difficulty = "easy";
            switch (i)
            {
                case 0: difficulty = "easy"; break;
                case 1: difficulty = "medium"; break;
                case 2: difficulty = "hard"; break;
            }
            Transform difficultyContainer = qnContainer.transform.GetChild(i);
            TextMeshProUGUI correct = difficultyContainer.GetChild(1).gameObject.GetComponent<TextMeshProUGUI>();
            results[i*2] = PlayerPrefs.GetInt(difficulty + "Correct", 0);
            correct.text = results[i*2].ToString();
            TextMeshProUGUI wrong = difficultyContainer.GetChild(2).gameObject.GetComponent<TextMeshProUGUI>();
            results[i*2 + 1] = PlayerPrefs.GetInt(difficulty + "Wrong", 0);
            wrong.text = results[i*2 + 1].ToString();
        }

        levelText = GameObject.Find("StatsPanel/LevelContainer/LevelValue").GetComponent<TextMeshProUGUI>();
        int level = PlayerPrefs.GetInt("level", 1);
        results[6] = level;
        levelText.text = level.ToString() + " / 60";

        int stage = PlayerPrefs.GetInt("stage", 1);
        int stagesCleared = PlayerPrefs.GetInt("stagesCleared", 0);
        if (level == 60 && stage ==  stagesCleared + 1)
        {
            PlayerPrefs.SetInt("stagesCleared", stagesCleared + 1);
        }

        timerText = GameObject.Find("StatsPanel/TimeContainer/TimeValue").GetComponent<TextMeshProUGUI>();
        float timer = PlayerPrefs.GetFloat("timer", 0f);
        string minutes = Mathf.Floor(timer / 60).ToString("00");
        string seconds = (timer % 60).ToString("00");
        results[7] = (int) timer;
        timerText.text = minutes + ":" + seconds;

        timerText = GameObject.Find("StatsPanel/ScoreContainer/ScoreValue").GetComponent<TextMeshProUGUI>();
        results[8] = CalculateScore(results);
        timerText.text = results[8].ToString();

        StartCoroutine(db.SaveResults(results));
    }

    /// <summary>
    /// Calculates score
    /// </summary>
    /// <param name="results"></param>
    /// <returns></returns>
    int CalculateScore(int[] results)
    {
        int correctBonus = 10;
        int wrongBonus = 2;
        int levelBonus = 5;
        float timeBonus = 0.25f;

        int score = 0;
        // Calculating correct question points
        for (int i=0; i<6; i+=2)
        {
            score += results[i] * (i + 1) * correctBonus;
        }

        // Calculating wrong question points
        for (int i = 1; i < 6; i += 2)
        {
            score += results[i] * (i + 1) * wrongBonus;
        }

        // Calculating level and time points
        score += results[6] * levelBonus;
        score += (int) (results[7] * timeBonus);
        return score;
    }

    /// <summary>
    /// Returns to main menu
    /// </summary>
    public void ReturnMainMenu()
    {
        SceneManager.LoadScene(0);
    }
}
