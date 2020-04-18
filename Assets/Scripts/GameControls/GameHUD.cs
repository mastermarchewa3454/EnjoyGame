using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// Script to display the game heads-up display (HUD)
/// </summary>
public class GameHUD : MonoBehaviour
{
    [SerializeField]
    private Text healthText;

    [SerializeField]
    private Text levelText;

    [SerializeField]
    private Text timerText;

    int health;
    int level;
    float timer;

    /// <summary>
    /// Gets the data from player prefs at the start of the level
    /// </summary>
    void Start()
    {
        health = PlayerPrefs.GetInt("health", 100);
        level = PlayerPrefs.GetInt("level", 1);
        timer = PlayerPrefs.GetFloat("timer", 0f);

        healthText.text = "Health: " + health + " / 100";
        levelText.text = "Level " + level;

        if (level == 1)
        {
            GameObject tutorial = transform.Find("TutorialText").gameObject;
            tutorial.SetActive(true);
        }
    }

    /// <summary>
    /// Saves the time at the end of the level
    /// </summary>
    void OnDestroy()
    {
        PlayerPrefs.SetFloat("timer", timer);
    }

    /// <summary>
    /// Updates the timer
    /// </summary>
    void Update()
    {
        timer += Time.deltaTime;
        string minutes = Mathf.Floor(timer / 60).ToString("00");
        string seconds = (timer % 60).ToString("00");
        timerText.text = minutes + ":" + seconds;
    }

    /// <summary>
    /// Updates the health on damage
    /// </summary>
    /// <param name="health">New health of player</param>
    public void UpdateHealth(int health)
    {
        healthText.text = "Health: " + health + " / 100";
    }
}
