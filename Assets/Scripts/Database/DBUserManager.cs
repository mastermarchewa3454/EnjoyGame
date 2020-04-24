using System.Collections;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;
using UnityEngine.Networking;

/// <summary>
/// DB manager for users
/// </summary>
public class DBUserManager : DBManager
{
    /// <summary>
    /// Registers a user
    /// </summary>
    /// <param name="username">Username of user</param>
    /// <param name="firstName">First name of user</param>
    /// <param name="lastName">Last name of user</param>
    /// <param name="classId">CLass of user</param>
    /// <param name="email">Email of user</param>
    /// <param name="password">Password of user</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator Register(string username,
                        string firstName,
                        string lastName,
                        string classId,
                        string email,
                        string password,
                        System.Action<string> callback)
    {
        string userDetails = "{\"username\":\"" + username + "\"," +
                              "\"firstName\":\"" + firstName + "\"," +
                              "\"lastName\":\"" + lastName + "\"," +
                              "\"className\":\"" + classId + "\"," +
                              "\"email\":\"" + email + "\"," +
                              "\"password\":\"" + password + "\"}";

        yield return StartCoroutine(PostData("/users", userDetails, callback: data => {
            Debug.Log(data);
            if (data.Contains("errors"))
            {
                Debug.Log("error");
                if (data.Contains("Record already exists"))
                {
                    Debug.Log("dupes");
                    callback("Username already exists");
                }
                else if (data.Contains(" with provided id is not found"))
                {
                    Debug.Log("class");
                    callback("Class not found");
                }
                else if (data.Contains("email"))
                {
                    Debug.Log("email");
                    callback("Email must match email pattern!");
                }
                else if (data.Contains("empty"))
                {
                    Debug.Log("empty");
                    callback("Fields cannot be empty!");
                }
                else
                {
                    Debug.Log("else");
                    callback("There has been an error");
                }
            }
            else
            {
                callback("success");
            }
        }));
    }

    /// <summary>
    /// Authenticates a user
    /// </summary>
    /// <param name="username">Username of user</param>
    /// <param name="password">Password of user</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator Login(string username, string password, System.Action<int> callback)
    {
        string loginDetails = "{\"username\":\"" + username + "\"," +
                               "\"password\":\"" + password + "\"}";
        string status = "empty";
        yield return StartCoroutine(PostData("/users/login", loginDetails, setAuth: true, callback: data => status = data));
        callback(int.Parse(status));
    }

    /// <summary>
    /// Gets details of user
    /// </summary>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator GetUser(System.Action<Student> callback)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string userString = "";
            yield return StartCoroutine(GetData("/users/" + userId, callback: data => userString = data));
            Student student = JsonUtility.FromJson<Student>(userString);
            PlayerPrefs.SetInt("stagesCleared", student.maxStageCanPlay);
            callback(student);
        }
    }

    /// <summary>
    /// Gets details of all users.
    /// </summary>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator GetUsers(System.Action<Student[]> callback)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string userString = "";
            yield return StartCoroutine(GetData("/users", callback: data => userString = data));

            Debug.Log(userString);
            Student[] students = JsonHelper.FromJson<Student>(userString);
            Debug.Log(students[0].username);
            callback(students);
        }
    }

    /// <summary>
    /// Gets details of a class.
    /// </summary>
    /// <param name="classId">classID of class</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator GetClasses(string classId, System.Action<Class> callback)
    {
        string classString = "";
        yield return StartCoroutine(GetData("/classes/" + classId, callback: data => classString = data));
        Class c = JsonUtility.FromJson<Class>(classString);
        Regex reg = new Regex("\"students\":(.|\n)+?]");
        MatchCollection matches = reg.Matches(classString);

        foreach (Match m in matches)
        {
            string students = m.Value;
            students = "{\"wrapperList\"" + m.Value.Substring(10) + "}";
            Debug.Log(students);
            Student[] sArr = JsonHelper.FromJson<Student>(students);
            foreach (Student s in sArr)
            {
                c.students.Add(s);
            }
        }
        callback(c);
    }

    /// <summary>
    /// Gets all classes
    /// </summary>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator GetClasses(System.Action<Class[]> callback)
    {
        string classString = "";
        yield return StartCoroutine(GetData("/classes", callback: data => classString = data));
        Class[] c = JsonHelper.FromJson<Class>(classString);
        Regex reg = new Regex("\"students\":(.|\n)+?]");
        MatchCollection matches = reg.Matches(classString);

        int i = 0;
        foreach (Match m in matches)
        {
            string students = m.Value;
            students = "{\"wrapperList\"" + m.Value.Substring(10) + "}";
            Debug.Log(students);
            Student[] sArr = JsonHelper.FromJson<Student>(students);
            foreach (Student s in sArr)
            {
                c[i].students.Add(s);
            }
            i++;
            callback(c);
        }
    }

    /// <summary>
    /// Gets the details of teacher
    /// </summary>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    public IEnumerator GetTeacher(System.Action<Teacher> callback)
    {
        string teacherString = "";
        yield return StartCoroutine(GetData("/teachers/" + userId, callback: data => teacherString = data));
        Teacher teacher = JsonUtility.FromJson<Teacher>(teacherString);
        callback(teacher);
    }
}

/// <summary>
/// Student class to help parse JSON
/// </summary>
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

/// <summary>
/// Class class to parse JSON
/// </summary>
[System.Serializable]
public class Class
{
    public string classId;
    public string className;
    public string teacherId;
    public string teacherFirstName;
    public string teacherLastName;
    public List<Student> students;
}

/// <summary>
/// Teacher class to parse JSON
/// </summary>
[System.Serializable]
public class Teacher
{
    public string firstName;
    public string lastName;
    public string teachClassId;
    public string teachClassName;
    public string userId;
}