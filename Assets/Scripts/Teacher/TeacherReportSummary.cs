using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
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

        teacherName.text = "hello";
        className.text = "3a";
    }

    /*public void PopulateList()
    {
        List<string> names = new List<string>()
        {
            "View All", "Betty", "Funny", "Hello" 
        };
        dropdown.AddOptions(names);
    }*/

    IEnumerator PopulateList()
    {
        yield return StartCoroutine(db.GetTeacher(callback:data => teacher = data));
        yield return StartCoroutine(db.GetClasses(teacher.teaches, callback: data => classes = data));
        List<string> names = new List<string>();
        foreach (Student s in classes.students)
        {
            names.Add(s.firstName);
        }
        dropdown.AddOptions(names);
    }
}
