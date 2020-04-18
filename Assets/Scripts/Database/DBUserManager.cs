using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;
using UnityEngine.Networking;

public class DBUserManager : DBManager
{
    public IEnumerator Register(string username,
                        string firstName,
                        string lastName,
                        string classId,
                        string email,
                        string password)
    {
        string userDetails = "{\"username\":\"" + username + "\"," +
                              "\"firstName\":\"" + firstName + "\"," +
                              "\"lastName\":\"" + lastName + "\"," +
                              "\"className\":\"" + classId + "\"," +
                              "\"email\":\"" + email + "\"," +
                              "\"password\":\"" + password + "\"}";

        yield return StartCoroutine(PostData("/users", userDetails, callback:data=>Debug.Log(data)));
        Debug.Log("Registered!");
    }

    public IEnumerator Login(string username, string password, System.Action<int> callback)
    {
        string loginDetails = "{\"username\":\"" + username + "\"," +
                               "\"password\":\"" + password + "\"}";
        string status = "empty";
        yield return StartCoroutine(PostData("/users/login", loginDetails, setAuth: true, callback: data => status = data));
        callback(int.Parse(status));
    }

    public IEnumerator GetUser(System.Action callback)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string userString = "";
            yield return StartCoroutine(GetData("/users/" + userId, callback: data => userString = data));

            Student student = JsonUtility.FromJson<Student>(userString);
            Debug.Log(student.userId);
            Debug.Log(student.username);
            Debug.Log(student.firstName);
            Debug.Log(student.lastName);
            Debug.Log(student.email);
            Debug.Log(student.maxStageCanPlay);
            PlayerPrefs.SetInt("stagesCleared", student.maxStageCanPlay);
            callback();
        }
    }

    public IEnumerator GetTeacher(System.Action callback)
    {
        string teacherString = "";
        yield return StartCoroutine(GetData("/users/" + userId, callback: data => teacherString = data));
        Teacher teacher = JsonUtility.FromJson<Teacher>(teacherString);
    }
}

[System.Serializable]
public class Student
{
    public string classId;
    public string className;
    public string email;
    public string firstName;
    public string lastName;
    public int maxStageCanPlay;
    public string userId;
    public string username;
}

[System.Serializable]
public class Teacher
{
    public string firstName;
    public string lastName;
    public string[] teaches;
    public string userId;
}

