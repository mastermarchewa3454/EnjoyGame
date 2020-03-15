using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.EventSystems;

public class StageSelection : MonoBehaviour
{
    public int stagesCleared = 0;

    void Start()
    {

        for (int i=0; i<=stagesCleared; i++)
        {
            Unlock(i+1);
        }
    }

    void Unlock(int i)
    {
        GameObject lockImage = GameObject.Find("Stage" + i + "Button/Lock");
        if (lockImage != null)
            lockImage.SetActive(false);
    }

    public void GoToStage(int level)
    {
        if (level <= stagesCleared + 1)
            SceneManager.LoadScene("Level 1");
        else
        {
            EventSystem.current.SetSelectedGameObject(null);

        }
    }
}
