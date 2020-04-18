using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Text.RegularExpressions;

public class DBClassManager : DBManager
{
    void Update()
    {
        if (Input.GetKeyDown("space"))
        {
            StartCoroutine(GetClasses());
        }
    }

    public IEnumerator GetClasses()
    {
        string classString = "";
        yield return StartCoroutine(GetData("/classes", callback: data => classString = data));
        Class[] c = JsonHelper.FromJson<Class>(classString);
        Regex reg = new Regex("\"students\":(.|\n)+?]");
        MatchCollection matches = reg.Matches(classString);

        Debug.Log(matches.Count);
        foreach (Match m in matches)
        {
            string students = m.Value;
            students = "\"wrapperList\"" + m.Value.Substring(10);
            Debug.Log(students);
        }
        /*Debug.Log(c[0].classId);
        Debug.Log(c[0].className);
        Debug.Log(c[0].teacherId);
        Debug.Log(c[0].teacherFirstName);
        Debug.Log(c[0].teacherLastName);
        Debug.Log(c[0].students);*/
    }

    [System.Serializable]
    public class Class
    {
        public string classId;
        public string className;
        public string teacherId;
        public string teacherFirstName;
        public string teacherLastName;
        public string students;
    }
}