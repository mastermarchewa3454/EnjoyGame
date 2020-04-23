using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Contains the methods that the MainMenu holds and utilizes 
/// </summary>
public class MainMenu : MonoBehaviour
{
    public void Start()
    {
        if (SceneManager.GetActiveScene().name.Equals("StartScreen") || SceneManager.GetActiveScene().name.Equals("TeacherScreen"))
        {
            string userId = PlayerPrefs.GetString("userId", null);
            string authHeader = PlayerPrefs.GetString("authHeader", null);
            int teacher = PlayerPrefs.GetInt("teacher", 0);
            PlayerPrefs.DeleteAll();
            PlayerPrefs.SetString("userId", userId);
            PlayerPrefs.SetString("authHeader", authHeader);
            PlayerPrefs.SetInt("teacher", teacher);
        }
    }

    /// <summary>
    /// Method call to Start the game
    /// </summary>
    public void PlayGame()
    {
        SceneManager.LoadScene("CharacterSelection");
    }

    /// <summary>
    /// Method call to view the leaderboard
    /// </summary>
    public void Leaderboard()
    {
        SceneManager.LoadScene("Leaderboard");
    }

    /// <summary>
    /// Method call to view the duo mode screen
    /// </summary>
    public void DuoMode()
    {
        SceneManager.LoadScene("DuoMode");
    }

    /// <summary>
    /// Method call to End the game.
    /// </summary>
    public void QuitGame()
    {
        Debug.Log("Exit Successful!");
        Application.Quit();
    }
    /// <summary>
    /// Method call to return to the main menu.
    /// </summary>
    public void ReturnMainMenu()
    {
        if (PlayerPrefs.GetInt("teacher") == 0)
            SceneManager.LoadScene("StartScreen");
        else
            EnterTeacherScreen();
    }
    /// <summary>
    /// Method call to enter the social menu of the game.
    /// </summary>
    public void EnterSocialMenu()
    {
        SceneManager.LoadScene("Social");
    }
    /// <summary>
    /// Method call to enter custom creation lobby to create a custom game.
    /// </summary>
    public void EnterCustomLobby()
    {
        SceneManager.LoadScene("QnA");
    }
    /// <summary>
    /// Method call to choose the class and students to view the report for the teacher.
    /// </summary>
    public void EnterTeacherReport()
    {
        if (PlayerPrefs.GetInt("teacher") == 1)
        {
            SceneManager.LoadScene("TeacherReport");
        }
        else
        {
            SceneManager.LoadScene("SearchingProfile");
        }
    }
    /// <summary>
    /// Method call to enter the main menu screen for the teacher.
    /// </summary>
    public void EnterTeacherScreen()
    {
        SceneManager.LoadScene("TeacherScreen");
    }
    /// <summary>
    /// Method call to view the summary reports that will include 
    /// 1)Stages cleared
    /// 2)Questions answer 
    /// 3)Overall progress report
    /// </summary>
    public void EnterReportSummary()
    {
        SceneManager.LoadScene("SummaryReportDetails");
    }
    /// <summary>
    /// Method call to redirect the user back to the login page.
    /// </summary>
    public void EnterLogIn()
    {
        SceneManager.LoadScene("LoginScreen");
    }
    /// <summary>
    /// Method call to redirect the user to the register page.
    /// </summary>
    public void EnterRegister()
    {
        SceneManager.LoadScene("RegisterScreen");
    }
    /// <summary>
    /// Method call to choose the class and students to view the report for the teacher.
    /// </summary>
    public void EnterCustomLobbyTeacherEdt()
    {
        PlayerPrefs.SetInt("Teacher", SceneManager.GetActiveScene().buildIndex);
        PlayerPrefs.Save();
        SceneManager.LoadScene("QnA");
    }

    /// <summary>
    /// Method call to redirect the user to the Custom Lobby screen.
    /// </summary>
    public void EnterCustomLobbyScreen()
    {
        if (PlayerPrefs.HasKey("Teacher"))
        {
            EnterTeacherScreen();
        }
        else
        {
            SceneManager.LoadScene("CustomLobbyScreen");
        }

    }

    /// <summary>
    /// Method call to redirect the user to the main menu page after clicking "Back" in Custom Lobby screen, depending on the user's role.
    ///</summary>
    public void LeaveFromCustomLobby()
    {
        if (PlayerPrefs.GetInt("Teacher") == 1)
        {
            EnterTeacherScreen();
        }
        else
        {
            ReturnMainMenu();
        }
    }

    /// <summary>
    /// Method call to redirect the user to the Join Custom Lobby Screen
    /// </summary>
    public void JoinCustomLobby()
    {
        SceneManager.LoadScene("JoinCustomLobby");
    }


    /// <summary>
    /// Method to call return the user back to the social screen.
    /// </summary>
    public void SocialScreen()
    {
        SceneManager.LoadScene("SocialScreen");
    }

    /// <summary>
    /// Method to call to direct the user to the searching profile screen.
    /// </summary>
    public void SearchingProfile()
    {
        SceneManager.LoadScene("SearchingProfile");
    }
}
