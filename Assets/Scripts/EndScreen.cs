using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class EndScreen : MonoBehaviour
{
    TextMeshProUGUI levelText;
    TextMeshProUGUI timerText;
    TextMeshProUGUI scoreText;
    int[] results;

    public void Start()
    {
        results = new int[8];
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
        int level = PlayerPrefs.GetInt("level", 0);
        results[6] = level;
        levelText.text = level.ToString() + " / 60";

        timerText = GameObject.Find("StatsPanel/TimeContainer/TimeValue").GetComponent<TextMeshProUGUI>();
        float timer = PlayerPrefs.GetFloat("timer", 0f);
        string minutes = Mathf.Floor(timer / 60).ToString("00");
        string seconds = (timer % 60).ToString("00");
        results[7] = (int) timer;
        timerText.text = minutes + ":" + seconds;

        timerText = GameObject.Find("StatsPanel/ScoreContainer/ScoreValue").GetComponent<TextMeshProUGUI>();
        timerText.text = CalculateScore(results).ToString();
    }

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

    public void ReturnMainMenu()
    {
        SceneManager.LoadScene(0);
    }
}
