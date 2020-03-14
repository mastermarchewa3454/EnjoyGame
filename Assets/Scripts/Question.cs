using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Question : MonoBehaviour
{
    public void GetBack()
    {
        SceneManager.LoadScene("Item Choice");
    }

    public void GetCorrect()
    {
        SceneManager.LoadScene("Item room");
    }

    public void GetWrong()
    {
        SceneManager.LoadScene("Item room");
    }
}
