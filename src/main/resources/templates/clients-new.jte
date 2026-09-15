@param String userEmail
@param String error

@template.layout.main(title = "New client", userEmail = userEmail, activeItem = "clients", content = @`
    <form method="post" action="/web/clients" class="col-12 col-md-6 col-lg-4">
        @if(!error.isEmpty())
        <div class="alert alert-danger">${error}</div>
        @endif

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" id="name" name="name" class="form-control" required autofocus>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="/web/clients" class="btn btn-outline-secondary">Cancel</a>
        </div>
    </form>
`)
