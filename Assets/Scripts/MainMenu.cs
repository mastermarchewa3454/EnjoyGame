using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    public void Start()
    {
        PlayerPrefs.DeleteKey("health");
        PlayerPrefs.DeleteKey("timer");
        PlayerPrefs.DeleteKey("level");
        PlayerPrefs.DeleteKey("treasure");
    }


    public void PlayGame()
    {
        SceneManager.LoadScene("StageSelection");
    }

    public void Leaderboard()
    {
        SceneManager.LoadScene("Leaderboard");
    }

    public void QuitGame()
    {
        Debug.Log("Sucessfully Exitted");
        Application.Quit();
    }

    public void ReturnMainMenu()
    {
        SceneManager.LoadScene("StartScreen");
    }

    public void EnterSocialMenu()
    {
        SceneManager.LoadScene("Social");
    }

    public void EnterCustomLobby()
    {
        SceneManager.LoadScene("QnA");
    }

    public void EnterTeacherReport()
    {
        SceneManager.LoadScene("TeacherReport");
    }

    public void EnterTeacherScreen()
    {
        SceneManager.LoadScene("TeacherScreen");
    }

    public void EnterReportSummary()
    {
        SceneManager.LoadScene("SummaryReportDetails");
    }
    public void EnterLogIn()
    {
        SceneManager.LoadScene("LoginScreen");
    }

}
