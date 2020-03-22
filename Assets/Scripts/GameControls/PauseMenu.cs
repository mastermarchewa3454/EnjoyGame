using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script for pausing the game
/// </summary>
public class PauseMenu : MonoBehaviour
{
    static bool GameIsPaused = false;

    [SerializeField]
    private GameObject pauseMenuUI;
    SceneChanger sceneChanger;

    /// <summary>
    /// Gets relevant components, and hides the pause menu UI
    /// </summary>
    void Start()
    {
        sceneChanger = FindObjectOfType<SceneChanger>();
        pauseMenuUI.SetActive(false);
    }

    /// <summary>
    /// Checks for pausing of game
    /// </summary>
    private void Update()
    {
        
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            if (GameIsPaused)
            {
                Resume();
            }
            else
            {
                Pause();
            }
        }
    }

    /// <summary>
    /// Resumes the game
    /// </summary>
    public void Resume()
    {
        pauseMenuUI.SetActive(false);
        Time.timeScale = 1f;
        GameIsPaused = false;
    }

    /// <summary>
    /// Pauses the game
    /// </summary>
    void Pause()
    {
        pauseMenuUI.SetActive(true);
        Time.timeScale = 0f;
        GameIsPaused = true;
    }

    /// <summary>
    /// Returns back to main menu screen
    /// </summary>
    public void QuitGame()
    {
        Resume();
        sceneChanger.ChangeToStartScene();
    }
}
