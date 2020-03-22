using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyMovement : MonoBehaviour
{
    [SerializeField]
    private float speed = 1;

    [SerializeField]
    private float movementInterval = 1;

    [SerializeField]
    private float stopInterval = 2;

    private Rigidbody2D rb;

    Vector2 movement;

    Animator anim;
    FireController enemyFire;
    bool moving = true;
    bool attacking = false;
    bool faceRight = false;

    void Start()
    {
        anim = GameObject.Find("Sprite").GetComponent<Animator>();
        rb = gameObject.GetComponent<Rigidbody2D>();
        enemyFire = gameObject.GetComponent<FireController>();
        StartCoroutine(NewHeading());
    }

    void FixedUpdate()
    {
        // Move sprite
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    IEnumerator NewHeading()
    {
        while (true)
        {
            // Alternates between idle, movement, idle, attack phase

            anim.SetBool("isMoving", moving);
            anim.SetBool("isAttacking", attacking);

            if (moving)
            {
                // Sets a new direction to head in
                NewHeadingRoutine();
                moving = false;
                yield return new WaitForSeconds(movementInterval);
            }
            else
            {
                // Stops moving
                StopHeading();

                if (attacking)
                {
                    enemyFire.Shoot();
                    moving = true;
                }
                attacking = !attacking;
                yield return new WaitForSeconds(stopInterval / 2);
            }
        }
    }

    void StopHeading()
    {
        movement.x = 0;
        movement.y = 0;
    }

    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1f, 1f);
        movement.y = Random.Range(-1f, 1f);

        // Flip sprite if moving in opposite direction
        if ((movement.x > 0 && !faceRight) || (movement.x < 0 && faceRight))
        {
            rb.transform.localScale = new Vector2(rb.transform.localScale.x * -1, 1);
            faceRight = !faceRight;
        }
    }
}
