using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.EventSystems;

/// <summary>
/// Handles stage selection
/// </summary>
public class StageSelection : MonoBehaviour
{
    [SerializeField]
    private int stagesCleared;

    /// <summary>
    /// Unlocks the appropriate number of stages
    /// </summary>
    void Start()
    {
        stagesCleared = PlayerPrefs.GetInt("stagesCleared", 0);
        for (int i=0; i<=stagesCleared; i++)
        {
            Unlock(i+1);
        }
    }

    /// <summary>
    /// Unlocks a stage by disabling the overlay
    /// </summary>
    /// <param name="stage">Stage to be unlocked</param>
    void Unlock(int stage)
    {
        GameObject lockImage = GameObject.Find("Stage" + stage + "Button/Lock");
        if (lockImage != null)
            lockImage.SetActive(false);
    }

    /// <summary>
    /// Checks if player can go to stage
    /// </summary>
    /// <param name="stage">Stage chosen</param>
    public void GoToStage(int stage)
    {
        if (stage <= stagesCleared + 1)
        {
            PlayerPrefs.SetInt("stage", stage);
            SceneManager.LoadScene("Level 1");
        }
        else
        {
            EventSystem.current.SetSelectedGameObject(null);

        }
    }
}
