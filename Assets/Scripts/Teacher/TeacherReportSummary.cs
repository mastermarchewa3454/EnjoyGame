using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

public class TeacherReportSummary : MonoBehaviour
{
    public TMP_Dropdown dropdown;
    public TMP_Text teacherName;
    public TMP_Text className;

    Teacher teacher;
    Class classes;
    DBUserManager db;

    // Start is called before the first frame update
    void Start()
    {
        db = GetComponent<DBUserManager>();
        
        StartCoroutine(PopulateList());
    }

    IEnumerator PopulateList()
    {
        yield return StartCoroutine(db.GetTeacher(callback:data => teacher = data));
        teacherName.text = "Welcome: " + teacher.firstName + " " + teacher.lastName;
        className.text = teacher.teachClassName;
        PlayerPrefs.SetString("className", teacher.teachClassName);

        yield return StartCoroutine(db.GetClasses(teacher.teachClassId, callback: data => classes = data));
        List<string> names = new List<string>();
        foreach (Student s in classes.students)
        {
            names.Add(s.firstName);
        }
        dropdown.AddOptions(names);
    }

    public void GenerateReport()
    {
        string userFirstName = dropdown.options[dropdown.value].text;
        if (userFirstName.Equals("View All"))
        {
            PlayerPrefs.SetString("otherUserId", teacher.teachClassId);
            PlayerPrefs.SetString("firstName", userFirstName);
        }
        else
        {
            foreach (Student s in classes.students)
            {
                if (userFirstName.Equals(s.firstName))
                {
                    PlayerPrefs.SetString("otherUserId", s.userId);
                    PlayerPrefs.SetString("firstName", userFirstName);
                }
            }
        }
        SceneManager.LoadScene("SummaryReportDetails");
    }
}
