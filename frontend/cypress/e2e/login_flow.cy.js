describe('The Login Page', () => {
    beforeEach(() => {
    })

    it('sets auth cookie when logging in via form submission', function () {

        const user = {
            email: "test@mail.ru",
            password: "sample"
        };

        cy.visit('/login')

        cy.get('input[id=email]').type(user.email)
        cy.get('input[name=password]').type(user.password)
        cy.get('button[type=submit]').click()

        cy.url().should('include', '/')
        cy.get('p[id=userEmail]').should('contain', user.email)
    })
})
