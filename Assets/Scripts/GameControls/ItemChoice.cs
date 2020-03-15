using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ItemChoice : MonoBehaviour
{
    public void GetHealth()
    {
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetAttack()
    {
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetSpeed()
    {
        SceneManager.LoadScene("QuestionDisplay");
    }
}
